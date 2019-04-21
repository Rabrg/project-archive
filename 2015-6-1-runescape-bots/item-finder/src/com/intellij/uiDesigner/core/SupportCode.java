//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intellij.uiDesigner.core;

import javax.swing.*;
import java.lang.reflect.Method;

public final class SupportCode {
    public SupportCode() {
    }

    public static TextWithMnemonic parseText(String textWithMnemonic) {
        if(textWithMnemonic == null) {
            throw new IllegalArgumentException("textWithMnemonic cannot be null");
        } else {
            int index = -1;
            StringBuffer plainText = new StringBuffer();

            for(int i = 0; i < textWithMnemonic.length(); ++i) {
                char ch = textWithMnemonic.charAt(i);
                if(ch == 38) {
                    ++i;
                    if(i >= textWithMnemonic.length()) {
                        break;
                    }

                    ch = textWithMnemonic.charAt(i);
                    if(ch != 38) {
                        index = plainText.length();
                    }
                }

                plainText.append(ch);
            }

            return new TextWithMnemonic(plainText.toString(), index);
        }
    }

    public static void setDisplayedMnemonicIndex(JComponent component, int index) {
        try {
            Method method = component.getClass().getMethod("setDisplayedMnemonicIndex", new Class[]{Integer.TYPE});
            method.setAccessible(true);
            method.invoke(component, new Object[]{new Integer(index)});
        } catch (Exception var3) {
            ;
        }

    }

    public static final class TextWithMnemonic {
        public final String myText;
        public final int myMnemonicIndex;

        private TextWithMnemonic(String text, int index) {
            if(text == null) {
                throw new IllegalArgumentException("text cannot be null");
            } else if(index == -1 || index >= 0 && index < text.length()) {
                this.myText = text;
                this.myMnemonicIndex = index;
            } else {
                throw new IllegalArgumentException("wrong index: " + index + "; text = \'" + text + "\'");
            }
        }

        public char getMnemonicChar() {
            if(this.myMnemonicIndex == -1) {
                throw new IllegalStateException("text doesn\'t contain mnemonic");
            } else {
                return Character.toUpperCase(this.myText.charAt(this.myMnemonicIndex));
            }
        }
    }
}
