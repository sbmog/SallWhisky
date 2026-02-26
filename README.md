# Sall Whisky Distillery - Production & Inventory Management

Dette projekt er udviklet som en del af 2. semester på Datamatikeruddannelsen. Formålet har været at skabe et specialiseret IT-system til at styre produktionsflows og lagerstyring for et moderne destilleri.

## Projektbeskrivelse
Systemet digitaliserer hele processen fra modtagelse af malt til det færdige produkt. Det sikrer fuld sporbarhed og overblik over whiskyens modningsproces, lagerplaceringer og kvalitetskontrol.

### Hovedfunktioner
* **Produktionsstyring:** Registrering og overblik over Malt Batches, Destillater og den efterfølgende påfyldning på fade.
* **Avanceret Lagerstyring:** Visuel håndtering af lageret via en TreeView-struktur, der dækker lagerhaller, reoler og specifikke hyldepladser.
* **Sporbarhed:** Mulighed for at spore en specifik flaske eller et fad tilbage gennem hele produktionshistorikken (fra fad til destillat til malt).
* **Modningskontrol:** Logik til beregning af "Angel's Share" (fordampning) og håndtering af omhældning mellem fade.
* **Tapning & Fortynding:** Styring af processen fra modnet whisky til færdige flasker, inklusiv beregning af alkoholprocent ved fortynding.

## Teknisk Stack
* **Sprog:** Java (JDK 17+)
* **GUI:** JavaFX (med brug af custom panes, TreeView og CSS-styling)
* **Test:** JUnit til unit-testing af forretningslogik
* **Udviklingsmetode:** Unified Process (UP)
* **Værktøjer:** IntelliJ IDEA, Git, UML (Enterprise Architect/StarUML)

## Arkitektur & Metodik
Projektet er opbygget efter en lagdelt arkitektur for at sikre separation of concerns:
1. **GUI Lag:** Håndterer brugerinteraktion via JavaFX.
2. **Controller Lag:** Fungerer som bindeled mellem UI og domænemodel (Controller-pattern).
3. **Model Lag:** En kompleks domænemodel, der afspejler destilleriets fysiske virkelighed.
4. **Storage Lag:** Centraliseret opbevaring af systemets tilstand.

Der er i udviklingen lagt stor vægt på **Systemudvikling (SU)**, herunder udarbejdelse af Use Cases, Domænemodeller og Design-klassediagrammer for at sikre en robust softwarearkitektur.

## Forfattere
Karsten Kirkegaard, Rune Hyldgaard Jensen og Sidse Borch Mogensen
