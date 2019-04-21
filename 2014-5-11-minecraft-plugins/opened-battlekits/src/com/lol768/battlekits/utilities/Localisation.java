package com.lol768.battlekits.utilities;

public class Localisation {
	/*
	 * messages: english: kitPermMSG:
	 * "You do not have permission to use this kit!" kitDisabled:
	 * "This kit is disabled in your current world (<WORLD NAME>)" kitNotFound:
	 * "Please choose a valid kit!" oneKitPerLife:
	 * "You may only use one kit per life!" noEmptyBowls:
	 * "You have no empty bowls!" cmdPermMSG:
	 * "You don't have permission to use this command" emptyBowlHand:
	 * "You must have an empty bowl in your hand" conf1: "Kit definitions"
	 * conf2:
	 * "This is where you can add your own kits and customise various details"
	 * conf3: "Global BattleKits settings" conf4:
	 * "Add world-specific blocks by warapping everything in the name of the world"
	 * conf5: "Restrictions are handled by permissions now." conf6:
	 * "This file contains the kit history, i.e. the last kit the user requested for respawning kits on death"
	 * conf7:
	 * "Also contains info on whether the player has used their kit in their life"
	 * conf8: "There is no reason to ever modify this file" confGen:
	 * "Generating config for <CONFIG FILE>" confSaveEror:
	 * "Could not save config to <FILE NAME>" confSaving: "Saving <CONFIG>..."
	 * kitAvailable: "You may now use a kit" kitNotFound:
	 * "That kit does not exist!" KitSignUsePermMSG:
	 * "You don't have permission to use kit signs" KitSignCreatePermMSG:
	 * "You don't have permission to create kit signs" kitSignMade:
	 * "Kit sign created successfully!" oneArgNeeded:
	 * "Need at least one argument" reloadedKits: "Kit config reloaded"
	 * reloadedGlobal: "Global config reloaded" playerNotFound:
	 * "Couldn't locate specified player" consoleCmdUsage:
	 * "Usage for console: /<command> <kit> <player>" indirectPermMSG:
	 * "You don't have permission for indirect kit distribution" economy1:
	 * "Purchase successful! You spent <amount> and now have <balance>"
	 * economy2:
	 * "You don't have enough money! This (kit) costs <amount> and you have <balance>"
	 * startup1: "Not enabling restrictions due to config setting" startup2:
	 * "Restrictions enabled. Use permissions to setup" startup8: "Vault found!"
	 * startup3: "Couldn't find Vault. Economy disabled for now." startup4:
	 * "TagAPI found." startup5:
	 * "Disabling tag functionality as TagAPI is not installed." startup6:
	 * "BattleKits (version) has been enabled!" startup7:
	 * "BattleKits has been disabled." startup8:
	 * "Couldn't create BattleKits data folder. Shutting down..." startup9:
	 * "Initializing configs..." french: kitPermMSG:
	 * "Vous n'avez pas la permission d'utiliser ce kit !" kitDisabled:
	 * "Ce kit est désactivé dans le monde (<WORLD NAME>)." kitNotFound:
	 * "Merci de choisir un kit valide !" oneKitPerLife:
	 * "Vous ne pouvez utiliser qu'un kit par vie !" noEmptyBowls:
	 * "Vous n'avez pas de bol vide !" cmdPermMSG:
	 * "Vous n'avez pas la permission d'utiliser ce commandement!"
	 * emptyBowlHand: "Vous devez avoir un bol vide dans votre main." conf1:
	 * "Définitions de kit" conf2:
	 * "C'est ici que vous pouvez ajouter vos propres kits et customiser plusieurs options."
	 * conf3: "Options générales de BattleKits" conf4:
	 * "Ajouter des blocks spécifiques au monde en ajoutant tout dans le nom du monde."
	 * conf5: "Les restrictions sont traitées par les permissions maintenant."
	 * conf6:
	 * "Ce fichier contient l'historique des kits, par exemple, le dernier kit qu'un joueur a demandé pour respawner avec."
	 * conf7:
	 * "Aussi, il contient des infos sur les joueurs s'ils ont utilisé leurs kits pendant le jeu."
	 * conf8: "Il n'y a aucune raison de modifier ce fichier." confGen:
	 * "Génération de la config pour <CONFIG FILE>." confSaveEror:
	 * "Impossible de sauvegarder la config pour <FILE NAME>." confSaving:
	 * "Sauvegarde de <CONFIG>..." kitAvailable:
	 * "Vous pouvez maintenant utiliser un kit." kitNotFound:
	 * "Merci de choisir un kit valide !" KitSignUsePermMSG:
	 * "Vous n'avez pas la permission d'utiliser des panneaux kit."
	 * KitSignCreatePermMSG:
	 * "Vous n'avez pas la permission de créer des panneaux de kit."
	 * kitSignMade: "Panneau de kit créé avec succès !" oneArgNeeded:
	 * "Vous devez saisir au moins un argument." reloadedKits:
	 * "Configuration des kits rechargée !" reloadedGlobal:
	 * "Configuration mondial rechargée!" playerNotFound:
	 * "Impossible de localiser ce joueur." consoleCmdUsage:
	 * "Utilisation pour la console : /<commande> <kit> <joueur>"
	 * indirectPermMSG:
	 * "Vous n'avez pas la permission pour donner des kits indirectement."
	 * economy1:
	 * "Achat réussi ! Vous avez utilisé <amount> et il vous reste <balance>."
	 * economy2:
	 * "Vous n'avez pas assez d'argent ! Ce kit vaut <amount> et vous avez <balance>."
	 * startup1: "Restrictions désactivées à cause de la configuration."
	 * startup2:
	 * "Restrictions activées. Utilisez les permissions pour configurer."
	 * startup8: "Vault trouvé !" startup3:
	 * "Impossible de trouver Vault. Economie désactivé pour l'instant."
	 * startup4: "TagAPI trouvé !" startup5:
	 * "Fonctionnalité de Tag désactivé car TagAPI n'est pas installé."
	 * startup6: "BattleKits (version) a été activé !" startup7:
	 * "BattleKits a été désactivé." startup8: "Vault trouvé !" startup9:
	 * "Initialisation de la configuration..." spanish: economy1:
	 * "Compra éxito! Pasaste <amount> y ahora tienen <balance>" economy2:
	 * "Usted no tiene suficiente dinero! Esto cuesta (kit) <amount> y usted tiene <balance>"
	 * startup1:
	 * "No se permitiendo restricciones debido a la opción de configuración"
	 * startup2:
	 * "Restricciones habilitado. Sírvase de permisos para configurar"
	 * startup8: "BattleKits ha encontrado Vault." startup3:
	 * "BattleKits no ha encontrado Vault. No hay apoyo para una economía"
	 * startup4: "BattleKits ha encontrado TagAPI" startup5:
	 * "BattleKits no ha encontrado TagAPI. No hay apoyo para este funcionalidad."
	 * startup6: "BattleKits (version) está habilitado" startup7:
	 * "BattleKits está desactivado" startup8:
	 * "BattleKits ha encontrado Vault." startup9: "Configs Inicializando..."
	 * kitPermMSG: "No tienes el permiso para este kit" kitDisabled:
	 * "Este kit está desahabilitado en tu mundo corriente (<WORLD NAME>)"
	 * kitNotFound: "El kit elegido no es válido." oneKitPerLife:
	 * "Sólo podrá utilizar un kit de por vida!" noEmptyBowls:
	 * "Usted no tiene cuencos vacíos!" cmdPermMSG:
	 * "No tienes el permiso para este kit" emptyBowlHand:
	 * "Usted tiene que tener un cuenco vacío en su mano" conf1:
	 * "Configuración de los kits" conf2:
	 * "Aquí es donde usted puede agregar sus propios kits y personalizar varios detalles"
	 * conf3: "Opciones globales para BattleKits" conf4:
	 * "Añadir mundial de bloques específicos envolviendo todo en el nombre del mundo"
	 * conf5: "Las restricciones son manejados por los permisos de ahora" conf6:
	 * "Este archivo contiene la historia kit, que es el último kit que el usuario solicitó para los kits de Respawning después de la muerte"
	 * conf7:
	 * "También contiene información sobre si el jugador ha usado su kit en su vida"
	 * conf8: "No hay razón alguna para modificar este archivo." confGen:
	 * "Generando configuración para <archivo de configuración>" confSaveEror:
	 * "No se pudo guardar config <FILE NAME>" confSaving:
	 * "Guardando <CONFIG>..." kitAvailable: "Los kits están ahora disponibles"
	 * kitNotFound: "El kit elegido no es válido." KitSignUsePermMSG:
	 * "No tienes el permiso para usar signos de kits" KitSignCreatePermMSG:
	 * "No tienes el permiso para crear signos de kits" kitSignMade:
	 * "El signo kit ha sido creado" oneArgNeeded:
	 * "Hay demasiado pocos argumentos" reloadedKits:
	 * "La configuración kit se ha recargado" reloadedGlobal:
	 * "La configuración global se ha recargado" playerNotFound:
	 * "Ese jugador no existe" consoleCmdUsage:
	 * "Uso para la consola: / <comando> <kit> <jugador>" indirectPermMSG:
	 * "Usted no tiene permiso para distribuir kit indirecta" german:
	 * kitPermMSG: "Du hast keine Berechtigung dieses Kit zu verwenden!"
	 * kitDisabled: "Dieses Kit ist in der Welt <WORLD NAME> nicht verfügbar"
	 * kitNotFound: "Bitte wähle ein gültiges Kit!" oneKitPerLife:
	 * "Du darfst nur ein Kit pro Leben verwenden!" noEmptyBowls:
	 * "Du besitzt keine leeren Schüsseln!" cmdPermMSG:
	 * "Du hast keine Berechtigungen diesen Befehl auszuführen" emptyBowlHand:
	 * "Du musst eine leere Schüssel in deiner Hand haben" conf1:
	 * "Kit Spezifikationen" conf2:
	 * "Hier kannst du eigene Kits einfügen und einige Details verändern"
	 * conf3: "Globale BattleKits Einstellungen" conf4:
	 * "Welt-spezifische Blöcke können durch Einfügen des Weltnamen davor und danach hinzugefügt werden"
	 * conf5: "Einschränkungen werden nun durch Permissions geregelt" conf6:
	 * "Diese Datei beinhaltet den Kit Verlauf, z.B. das letzte Kit, das ein User angefragt hat"
	 * conf7:
	 * "Sie beinhaltet ebenfalls die Information, ob der Spieler bereits ein Kit im aktuellen Leben verwendet hat"
	 * conf8: "Es gibt keinen Grund diese Datei zu modifizieren." confGen:
	 * "Generiere Konfiguration für <CONFIG FILE>" confSaveEror:
	 * "Konfiguration konnte nicht in <FILE NAME> gespeichert werden"
	 * confSaving: "Speichere <CONFIG>..." kitAvailable:
	 * "Du kannst dieses Kit nun verwenden" kitNotFound:
	 * "Bitte wähle ein gültiges Kit!" KitSignUsePermMSG:
	 * "Du hast keine Berechtigungung um Kit-Schilder zu benutzen!"
	 * KitSignCreatePermMSG:
	 * "Du hast keine Berechtigung, Kit-Schilder zu erstellen!" kitSignMade:
	 * "Das Kit-Schild wurde erfolgreich erstellt!" oneArgNeeded:
	 * "Es wird mindestens ein Argument benötigt" reloadedKits:
	 * "Kit Konfiguration wurde von der Datei geladen" reloadedGlobal:
	 * "Globale Konfiguration wurde von der Datei geladen" playerNotFound:
	 * "Dieser Spieler konnte nicht lokalisiert werden" consoleCmdUsage:
	 * "Verwendung für die Konsole: /<command> <kit> <player>" indirectPermMSG:
	 * "Du hast keine Rechte Kits indirekt auszugeben" economy1:
	 * "Kauf erfolgreich! Du hast <amount> ausgegeben und hast nun <balance>"
	 * economy2:
	 * "Du besitzt nicht genügend Geld! Dieses (kit) kostet <amount> und du besitzt nur <balance>"
	 * startup1:
	 * "Aufgrund der Änderungen in der den Einstellungen können die Einschränkungen nicht geladen werden."
	 * startup2:
	 * "Die Einschränkungen wurden aktiviert. Verwenden sie Berechtigungen um diese zu konfigurieren"
	 * startup8: "Vault gefunden!" startup3:
	 * "Vault konnte nicht gefunden werden. Daher wird die Economy-Funktion deaktiviert."
	 * startup4: "TagAPI wurde gefunden" startup5:
	 * "Die Tag-Unterstützung wird deaktiviert da TagAPI nicht gefunden wurde"
	 * startup6: "BattleKits (version) wurde geladen!" startup7:
	 * "BattleKits wurde deaktiviert." startup8: "Vault gefunden!" startup9:
	 * "Konfiguration wird initialisiert..." italian: kitPermMSG:
	 * "Non hai il permesso di utilizzare questo kit!" kitDisabled:
	 * "Questo kit è disabilitato nel tuo mondiale attuale (<WORLD NAME>)"
	 * kitNotFound: "Si prega di scegliere un kit valido!" oneKitPerLife:
	 * "È possibile utilizzare solo uno kit per la vita!" noEmptyBowls:
	 * "Non hai ciotole vuote!" cmdPermMSG:
	 * "Non hai i permessi per utilizzare questo comando" emptyBowlHand:
	 * "È necessario disporre di una ciotola vuota nella tua mano" conf1:
	 * "Kit definizioni" conf2:
	 * "Questo è dove è possibile aggiungere il proprio kit e personalizzare vari dettagli"
	 * conf3: "Impostazioni globali BattleKits" conf4:
	 * "Aggiungere mondiale specifici blocchi eseguendo il wrapping ogni cosa nel nome del mondo"
	 * conf5: "Restrizioni sono gestiti da autorizzazioni non ora." conf6:
	 * "Questo file contiene la cronologia kit, che è l'ultimo kit l'utente ha richiesto per i kit di respawning in caso di morte"
	 * conf7:
	 * "Contiene anche informazioni su se il giocatore ha usato il loro kit nella loro vita"
	 * conf8: "Non vi è alcuna ragione di modificare mai questo file" confGen:
	 * "Creazione di configurazione per <CONFIG FILE>" confSaveEror:
	 * "Impossibile salvare configurazione per <FILE NAME>" confSaving:
	 * "Salvando <CONFIG>..." kitAvailable:
	 * "È ora possibile utilizzare un kit di" kitNotFound:
	 * "Si prega di scegliere un kit valido!" KitSignUsePermMSG:
	 * "Non hai il permesso di utilizzare i kit da segni" KitSignCreatePermMSG:
	 * "Non si dispone dell'autorizzazione per creare kit di segni" kitSignMade:
	 * "Kit Segni creato con successo!" oneArgNeeded:
	 * "Hai bisogno di almeno un argomento" reloadedKits:
	 * "Kit di configurazione ricaricati" reloadedGlobal:
	 * "Globale config ricaricati" playerNotFound:
	 * "Impossibile trovare giocatore specificato" consoleCmdUsage:
	 * "Utilizzo per console: / <command> <kit> <player>" indirectPermMSG:
	 * "L'utente non può distribuire kit indirettamente" economy1:
	 * "Acquisto di successo! Hai passato <amount> ed ora hanno <balance>"
	 * economy2:
	 * "Non hai abbastanza soldi! Questo (kit) costi <amount> e si dispone di <balance>"
	 * startup1:
	 * "Non consentendo restrizioni a motivo di impostazione nella configurazione"
	 * startup2:
	 * "Restrizioni abilitata. Utilizzare le autorizzazioni per impostare"
	 * startup8: "Vault trovato!" startup3:
	 * "Non poteva trovare Vault. Economia disabilitato per ora." startup4:
	 * "TagAPI trovato." startup5:
	 * "Disattivazione la funzionalità tag da usare come TagAPI non è installato."
	 * startup6: "BattleKits (version) è stato abilitato!" startup7:
	 * "BattleKits è stato disabilitato." startup8: "Vault trovato!" startup9:
	 * "Configurazioni Inizializzazione delle..."
	 */
}
