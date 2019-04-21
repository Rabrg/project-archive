package com.gmail.rabrg96.squad.dataset;

import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;

import java.io.StringReader;
import java.util.*;


//see:  https://github.com/tamingtext/book/blob/master/src/main/java/com/tamingtext/qa/AnswerTypeContextGenerator.java 
//see:  https://github.com/yawenl/NLP_DEMO/tree/master/src

public class RecUtterance {

    boolean TenseFlag = false;
    boolean ModalFlag = false;
    boolean SimpleFlag = false;
    boolean ContinuousFlag = false;
    boolean PerfectFlag = false;
    boolean PerfectContinuousFlag = false;

    public static enum ClassUsageMode {
        DIALOG, READING
    }

    public static enum Question_Types {
        WHO, WHOSE, WHAT, WHICH, WHEN, HOW, WHY, WHERE, CAN, WILL, COULD, SHOULD, WOULD, IS, ARE, AM, SHALL, MAY, DID, DO
    }

    public static enum Tense_Direction {
        PAST, PRESENT, FUTURE
    }

    public static enum Tense_Modifier {
        SIMPLE, CONTINUOUS, PERFECT, PERFECT_CONTINUOUS
    }

    public ClassUsageMode mode;

    Tense_Direction SentenceTenseDirection;
    Tense_Modifier SentenceTenseModifier;

    public static String SBARQ = "SBARQ";
    public static String SQ = "SQ";

    String utterance = "";

    //Information Seeking Verbs:
    HashSet<String> verbs = new HashSet<String>(Arrays.asList("say me", "tell me", "know", "name", "recommend"));

    //Wh* Word Features:
    HashSet<String> whWords = new HashSet<String>(Arrays.asList("where", "when", "why", "who", "what", "whom", "how"));
    //Interrogative Feature:
    HashSet<String> condWords = new HashSet<String>(Arrays.asList("could", "should"));


    public RecUtterance(ClassUsageMode mode) {
        this.mode = mode;
    }


    // For now, let's assume all utterances are one sentence long
    public String processUtterance(String Utterance) {


        this.utterance = Utterance.trim();
        System.out.println();
        System.out.println(">> " + utterance);

        if (utterance.equals("")) {
            System.out.println("Sampi Warning: No Utterance to process. " + this.getClass());
            return null;
        }


        // For now, we'll put an arbitrary limit on the size of an utterance.
        if (utterance.length() > 500) {
            System.out.println("Sampi Warning: Utterance length is too long. " + this.getClass());
            return null;
        }

        // For more complicated scenarios, we'll run the full Parser.
        return parseUtterance();
    }

    //================================
    //	FIND THE TENSE
    //================================
    private void getSentenceTense(String pos) {
        if (pos.equalsIgnoreCase("VBD")) // I ate pizza.
            SentenceTenseDirection = Tense_Direction.PAST;
        if (pos.equalsIgnoreCase("VB")) TenseFlag = true;
        if (pos.equalsIgnoreCase("MD")) ModalFlag = true;
        if ((TenseFlag == true) && (ModalFlag == true))  // I will eat pizza.
            SentenceTenseDirection = Tense_Direction.FUTURE;
        if (((TenseFlag == true) && (ModalFlag == false)) || (pos.equalsIgnoreCase("VBP")))  // I eat pizza.
            SentenceTenseDirection = Tense_Direction.PRESENT;

        // ============== Get Type of Tense: Simple, Continuous, Perfect, Perfect Continuous
        if (pos.equalsIgnoreCase("VBG")) {
            ContinuousFlag = true;
            SentenceTenseModifier = Tense_Modifier.CONTINUOUS; // Continuous: eating, smoking, ...
        }

        if (pos.equalsIgnoreCase("VBN")) {
            PerfectFlag = true;
            SentenceTenseModifier = Tense_Modifier.PERFECT; // Perfect: eaten
        }

        if ((ContinuousFlag == true) && (PerfectFlag == true)) {
            PerfectContinuousFlag = true;
            PerfectFlag = false;
            ContinuousFlag = false;
            SentenceTenseModifier = Tense_Modifier.PERFECT_CONTINUOUS; // I will have been eating pizza.
        }

        if ((PerfectFlag || ContinuousFlag || PerfectContinuousFlag)) {
        } else // Perfect Simple: I ate pizza.
            SentenceTenseModifier = Tense_Modifier.SIMPLE;

    }

    private void debugTense() {
        System.out.print("TENSE: ");
        if (SentenceTenseDirection == Tense_Direction.PAST)
            System.out.print("Past ");
        if (SentenceTenseDirection == Tense_Direction.PRESENT)
            System.out.print("Present ");
        if (SentenceTenseDirection == Tense_Direction.FUTURE)
            System.out.print("Future ");

        if (SentenceTenseModifier == Tense_Modifier.SIMPLE)
            System.out.print("Simple");
        if (SentenceTenseModifier == Tense_Modifier.CONTINUOUS)
            System.out.print("Continuous");
        if (SentenceTenseModifier == Tense_Modifier.PERFECT)
            System.out.print("Perfect");
        if (SentenceTenseModifier == Tense_Modifier.PERFECT_CONTINUOUS)
            System.out.print("Perfect Continuous");

        System.out.println("");
    }


    private String parseUtterance() {
        String targetWord;
        String tempNER = "";

        Annotation document = new Annotation(utterance);
//		TODO: change StartSampi.pipeline.annotate(document);

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // Combination of flags determine the Tense (for example, VB + MD = Future Simple)
            TenseFlag = false;
            ModalFlag = false;
            ContinuousFlag = false;
            PerfectFlag = false;
            PerfectContinuousFlag = false;

            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

                // Get the actual word and the lemma
                String word = token.get(TextAnnotation.class);
                String lemma = token.get(LemmaAnnotation.class);
                //if (! word.equalsIgnoreCase(lemma))
                //	System.out.println("Word-not-lemma: " + lemma);

                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                //System.out.println("Part of Speech: " +word +": " + pos);

                //if (pos.equalsIgnoreCase("NN") || pos.equalsIgnoreCase("NNP") || pos.equalsIgnoreCase("NNPS") || pos.equalsIgnoreCase("NNS"))
                //	System.out.println (" >>  Noun=" + word);


                // Find the tense of the sentence. Tense is determined by the verb, and its plural state.
                if ((pos.startsWith("V")) || (pos.equals("MD")))
                    getSentenceTense(pos);

                //============================ 		NAMED ENTITIES
                // Named Entity Resolution: this is the NER label of the token
                // Options include: "PERSON", "LOCATION", "ORGANIZATION", "DATE", "NUMBER", "PERCENT", maybe more??
                String ne = token.get(NamedEntityTagAnnotation.class);
                //System.out.println(ne);

                if ("PERSON".equals(ne) || "LOCATION".equals(ne) || "ORGANIZATION".equals(ne))
                    tempNER = tempNER + " " + word;

                if (!(("PERSON".equals(ne) || "LOCATION".equals(ne) || "ORGANIZATION".equals(ne)))) {
                    if (!tempNER.trim().equals(""))
                        System.out.println(" |--> Named Entity: " + tempNER);
                    tempNER = "";
                }

            }
            //debugTense();

            LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
            //lp.setOptionFlags(" -makeCopulaHead"); //This didn't do anything; also tried setting properties in StartSampi.java


            TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
            List<CoreLabel> rawWords = tokenizerFactory.getTokenizer(new StringReader(utterance)).tokenize();
            Tree bestParse = lp.parseTree(rawWords);


            //============================
            PennTreebankLanguagePack tlp = new PennTreebankLanguagePack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
            GrammaticalStructure gs = gsf.newGrammaticalStructure(bestParse);

            System.out.println("++++++++++");
            System.out.println(gs.typedDependencies());
            System.out.println("++++++++++");


            List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
            System.out.println(tdl);
            System.out.println("++++++++++");

            //============================    First pass scans for the Predicate only
            int predicateIndex = 0;
            for (int indx = 0; indx < tdl.size(); indx++) {
                debugTypedDependencies(tdl, indx);

                if (tdl.get(indx).reln().toString().equals("root")) {
                    System.out.println("+++ The Predicate is: " + tdl.get(indx).dep().word());
                    predicateIndex = tdl.get(indx).dep().index();
                }

            }

            //============================    Second Pass scans for Subject and Object
            // At this point, the subject/object could be 'partial' (e.g., "Smith", when you want "John Smith");
            // Still need to cross-reference with NER for full subject/object
            for (int indx = 0; indx < tdl.size(); indx++) {

                if ((tdl.get(indx).reln().toString().equals("nsubj") || tdl.get(indx).reln().toString().equals("nsubjpass")) && (tdl.get(indx).gov().index() == predicateIndex)) {
                    System.out.println("+++ The Subject is: " + tdl.get(indx).dep().word());
                }

                if (tdl.get(indx).reln().toString().equals("dobj")) {
                    System.out.println("+++ The Object is: " + tdl.get(indx).dep().word());
                }

                //System.out.println(indx +": " +tdl.get(indx).gov().index() +" <--> " + predicateIndex );
            }

            //============================
            ConstituentFactory confac = LabeledConstituent.factory();

            Iterator it = bestParse.iterator();
            System.out.println("BEST PARSE, Yield Words: " + bestParse.yieldWords());
            System.out.println("BEST PARSE, Constituents: " + bestParse.constituents());
            System.out.println("BEST PARSE, Children as List: " + bestParse.getChildrenAsList());
            System.out.println("BEST PARSE, Yield: " + bestParse.yield());
            System.out.println("BEST PARSE, Yield Has Word: " + bestParse.yieldHasWord());
            System.out.println("BEST PARSE, Penn String: " + bestParse.pennString());
            System.out.println("BEST PARSE, Children: " + bestParse.children());
            System.out.println("BEST PARSE, flatten: " + bestParse.flatten());
            System.out.println("BEST PARSE, toArray: " + bestParse.toArray());
            System.out.println("BEST PARSE, printLocalTree");
            bestParse.printLocalTree();

            Tree tr = (Tree) it.next();
            Set constituents = tr.constituents(confac);
            System.out.println("Constituents:" + constituents);

            if (mode != ClassUsageMode.DIALOG) // Only process questions in Dialog Mode.
            {
                for (Iterator it2 = constituents.iterator(); it2.hasNext(); ) {
                    Constituent c = (Constituent) it2.next();
                    targetWord = c.label().toString();
                    System.out.print(" <" + c.label() + "> " + targetWord + ",");

                    if (targetWord.equals(SBARQ) | targetWord.equals(SQ)) {
                        if (utterance.startsWith("who")) return processWhoQuestion();
                        if (utterance.startsWith("whose")) return processWhoseQuestion();
                        if (utterance.startsWith("what")) return processWhatQuestion();
                        if (utterance.startsWith("which")) return processWhichQuestion();
                        if (utterance.startsWith("when")) return processWhenQuestion();
                        if (utterance.startsWith("how")) return processHowQuestion();
                        if (utterance.startsWith("why")) return processWhyQuestion();
                        if (utterance.startsWith("where")) return processWhereQuestion();
                        if (utterance.startsWith("can")) return processCanQuestion();
                        if (utterance.startsWith("will")) return processWillQuestion();
                        if (utterance.startsWith("could")) return processCouldQuestion();
                        if (utterance.startsWith("should")) return processShouldQuestion();
                        if (utterance.startsWith("would")) return processWouldQuestion();
                        if (utterance.startsWith("is")) return processIsQuestion();
                        if (utterance.startsWith("are")) return processAreQuestion();
                        if (utterance.startsWith("am")) return processAmQuestion();
                        if (utterance.startsWith("shall")) return processShallQuestion();
                        if (utterance.startsWith("may")) return processMayQuestion();
                        if (utterance.startsWith("did")) return processDidQuestion();
                        if (utterance.startsWith("do")) return processDoQuestion();
                        if (utterance.startsWith("does")) return processDoesQuestion();
                        return "RecUtterance: Identified a question, but is confused.";
                    }
                }
            }
        }
        System.out.println("");
        return null;
    }


    private String processWhoQuestion() {
        return "who? none of your business!";
    }

    private String processWhoseQuestion() {
        return "whose? it's mine!";
    }

    private String processWhatQuestion() {
        return "what? whatever!";
    }

    private String processWhichQuestion() {
        return "which? whichever one I want!";
    }

    private String processWhenQuestion() {
        return "when? now - or maybe later";
    }

    private String processHowQuestion() {
        return "how? like this.";
    }

    private String processWhyQuestion() {
        return "why? because that's the way I like it.";
    }

    private String processWhereQuestion() {
        return "where? here, or there.";
    }

    private String processCanQuestion() {
        return "Can I? sure.";
    }

    private String processWillQuestion() {
        return "will I? probably not.";
    }

    private String processCouldQuestion() {
        return "Could I? it's possible.";
    }

    private String processShouldQuestion() {
        return "Should I? I doubt it.";
    }

    private String processWouldQuestion() {
        return "Would I? I'll think about it.";
    }


    private String processIsQuestion() {
        return "Is? hmm.";
    }

    private String processAreQuestion() {
        return "Are? hmm.";
    }

    private String processAmQuestion() {
        return "Am I? sure.";
    }

    private String processShallQuestion() {
        return "Shall we? sure.";
    }

    private String processMayQuestion() {
        return "May I? sure.";
    }

    private String processDidQuestion() {
        return "Did I? sure.";
    }

    private String processDoQuestion() {
        return "Do I? sure.";
    }

    private String processDoesQuestion() {
        return "Does it? I think so.";
    }

    private void debugTypedDependencies(List<TypedDependency> tdl, int indx) {
        System.out.println("");
        System.out.println("FULL:" + tdl.get(indx));

        System.out.println("RELATION: " + tdl.get(indx).reln());
        System.out.println("RELATION, SHORT: " + tdl.get(indx).reln().getShortName());
        System.out.println("RELATION, SPECIFIC: " + tdl.get(indx).reln().getSpecific());

        System.out.println("DEPENDENCY WORD: " + tdl.get(indx).dep().word());
        System.out.println("DEPENDENCY INDEX: " + tdl.get(indx).dep().index());

        System.out.println("GOV WORD: " + tdl.get(indx).gov().word());
        System.out.println("GOV INDEX: " + tdl.get(indx).gov().index());

    }

}