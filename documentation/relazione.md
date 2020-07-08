# RELAZIONE

## INDICE
1. <a href=#description> DESCRIZIONE DELL' AVVENTURA</a>
2. <a href=#details> DETTAGLI E SCELTE PROGETTUALI</a>
3. <a href=#commands> COMANDI DI GIOCO</a>

## <h2 id="description"> 1. Descrizione  dell'avventura <h2>
Lo sviluppo del progetto è stato teso a realizzare più che una semplice avventura testuale standalone, un vero e proprio "Game engine" affinchè fosse possibile implementare diverse avventure testuali, senza la necessità di dovere cambiare troppo codice.
L'engine di gioco offre anche la possibiltà di utilizzare una gui al posto della linea di comando.

### 1.1 Descrizione della storia 
Il gioco presentato, Wutond, è una reinterpretazione in chiave goliardica e autoironica della realtà della nostra città e tratta le (dis-)avventure di un ipotetico, quanto improbabile detective inviato dal nord Italia ad investigare sull'omicidio del propietario del bar più in voga della città, durante la quale il detective dovrà interfacciarsi con l'incomprensibile, almeno all'inizio, lingua locale e con alcune delle più famose e controverse personalità cittadine fino alla scoperta dell'assassino e del suo movente.

### 1.2 Alcune funzionalità
La prima azione compiuta dall'engine è quella di inizializzare tutti i componenti dell'avventura leggendo ed interpretando una serie di file di tipo txt, presenti nella cartella **res/file_txt**, editabili manualmente e scritti utilizzando una struttura custom e standard (ricorda molto la struttura dei file di configurazione json), dimodochè fosse facilmente modificabile per qualsiasi tipologia di avventura.
I file gestiscono tutti gli oggetti, npc, stanze ecc.., e diverse componenti relative al **parser** come Comandi,Articoli,Preposizioni ecc..

Una volta avviato il gioco si apre l'interfaccia grafica composita di un'area di testo in output non editabile e una dedicata all'input dei comandi, viene stampata a video una lista di comandi e possibili combinazioni e subito dopo parte la storia.
C'è anche la possibilità di caricare una precedente partita da un file di salvataggio o di salvarne una nuova inserendo l'apposito comando **salva**.

E' possibile giocare, muoversi ed esplorare il mondo inserendo i comandi che verranno interpretati ed eseguiti, se corretti.

E' presente anche una finestra JDialog dedicata ai dialoghi con gli Npc, che riproduce una tipica finestra a scelta multipla gdr.

![JDialog](documentation/res/JDialog.PNG)

## <h2 id="details"> 2. Dettagli e Scelte Progettuali <h2>

Il progetto è diviso nei seguenti package:
- events
- exceptions
- fileInitializer
- core
- games
- parser
- type
- utils
- gui

La divisione in package è stata realizzata tenendo conto dei punti in comune delle classi presenti all'interno in modo che fossero facilmente riconosciute e distinte.

### 2.1 Package "core"
Il package "core", come dice il nome stesso, contiene le due classi principali del game engine che garantiscono la sua estendibiltà : **Engine** e **GameDescription**.

La classe astratta **GameDescription** contiene tutti i campi utili all'implementazione dei vari giochi, come le liste degli oggetti, la stanza corrente ecc.. e definisce metodi astratti (da reimplementare necessariamente nelle classi che ereditano e costituiscono i giochi veri e propri) come init() per inizializzare tutti i file, e nextMove(), che esegue il comando parsato.

La classe **Engine** contiene un'istanza di GameDescription e dell' interfaccia grafica, ed è l' unico entry point del progetto in quanto contiene il metodo main() che a sua volta richiama un metodo run() che consente di inserire il comando, parsarlo ed eseguirlo.

### 2.2 Package "games"
Il package "games" è designato a contenere tutte le classi che ereditano da **GameDescription** e rappresentano le avventure vere e proprie.

### 2.3 Package "fileInitializer"
Il package "fileInitializer" contiene le tre classi che inizializzano da file contenuti nella cartella **res/file_txt** : 
- componenti di gioco (oggetti, stanze ecc..) -> **FileInit**
- utilià di gioco (comandi, articoli, preposizioni ecc..) -> **GameUtil**
- la grammatica e i simboli terminali, non terminale e le produzioni -> **GrammarInit** 

### 2.4 Package "types"
Il package "types" contiene tutte le classi che rappresentano i tipi, sia dei componenti di gioco, come **AdvObject** o **Room**, che delle utilità di gioco come **Command**.

### 2.5 Package "utils"
Il package "utils" contiene tutte le classi di utilità e che si suppone vengano riutilizzate nel corso dello sviluppo, come ad esempio **GameList**, una classe template "wrapper" che rappresenta una struttura dati custom di tipo lista, creata per poter lavorare agilmente con i nomi e gli id associati ai vari componenti di gioco, dal momento che tutti gli oggetti stanze ecc.. si trovano nelle liste associate.

### 2.6 Package "gui" 
Il package "gui" contiene tutte le classi di interfaccia grafica, come **Gui** o **DialogB** che sono le principali finestre di interazione previste dall'engine.
La prima per il normale flusso di gioco, la seconda dedicata all'interazione con gli Npc.

### 2.7 Package "events"
Il package "events" contiene un'interfaccia **EventInterface** e una classe che la implementa **EventHandler**. 
L'idea è quella di fornire uno standard per eseguire dei check su determinati trigger, che permettono di cambiare il flusso narrativo, descrizioni, stanze, comportamenti ecc.. all'interno del gioco in modo che fosse chiara la ridefinizione per eventuali eventi custom.

###  2.8 Package "parser"
Il package "parser" contiene tutte le classi utili a parsare la stringa di comando dell'utente.

Esso contiene un'interfaccia **Parser** implementata in **ItParser** per renderlo indipendente dalla lingua che si vuole utilizzare. 

Esso contiene un metodo **parse** che analizza la stringa in input, la tokenizza, ricerca una corrispondenza tra i token e gli elementi del gioco avvalendosi di nomi e alias, consente di ricercare anche token composti da due parole, e nel caso in cui i token vengano riconosciuti, essi vengono catalogati in una lista di stringhe rappresentati le categorie sintattiche di appartenenza e passati alla classe **Cyk** presente nel sub-package grammar. 
Nel caso in cui almeno uno dei token non venga riconosciuto il programma lancia un'eccezione.

Nel caso il controllo in Cyk vada a buon fine, **ItParser** restituisce un **ParserOutput**, che contiene le istanze degli oggetti di lavoro che verrà analizzata ed eventualmente eseguita nel metodo **nextMove()** di **GameDescription**.

### 2.8.1 Package "grammar"
Il package "grammar" contiene le classi utili a verificare la correttezza sintattica della stringa passata in input.

Esso contiene la classe **CFGrammar** , che contiene attributi utili a descrivere una grammatica Context Free come : 
- simbolo di start
- lista delle produzioni
- lista dei non terminali
- lista dei terminali

Mediante l'apposito file è possibile ampliare la grammatica specificando ulteriori simboli o produzioni( Necessariamente in forma normale di Chomsky !).

Le classi **Production** e **ProductionSide** per agevolare la costruzione delle produzioni della grammatica, specificando tra rightSide e leftSide.

La classe **Cyk** descrive l'algoritmo cyk utilizzato per discernere se una lista di stringhe in input, possa essere derivata dai simboli definiti e dalle produzioni della grammatica a partire dal simbolo di START.  
 
 ### 2.9 Package "exception"
 Il package "exception" contiene due classi che estendono la classe **Exception** : 
 - **NullOutputException** , lanciata nel caso il parse non riesca a riconoscere una componente del gioco, ma l'azione sia specificata in modo corretto, generando un messaggio personalizzato variabile per ogni azione.

 - **EOGameException** lanciata nell'evento associato alla fine del gioco, e permette di distinguere tra un good-ending o un bad-ending

 ## <h2 id="commands"> 3. Comandi di gioco <h2>
 I comandi implementati nel game engine sono:
 - **nord, sud, est, ovest** (con rispettivi alias), per la navigazione all'interno del mondo di gioco;

 - **vai** + **nord, sud, est, ovest**, la controparte più discorsiva dei comandi di navigazione

 - **salva** + **nomefile** per salvare il gioco, in file specificati dall'utente (basta solo il nome);

 - **carica** + **nomefile** la controparte di salva, serve a caricare un salvataggio;

 - **guarda** / **guarda** + **oggetto/npc/porta/container** (con rispettivi alias ad es. osserva..), il comando chiave nell'avventura in quanto consente di ottenere descrizioni più approfondite su stanze (se si specifica solo "guarda") oppure su tutti gli elementi della stanza (ad es. "guarda la chiave"), come npc, oggetti, container ed oggetti dell'inventario;

 - **inventario** stampa a video la lista degli oggetti contenuti nell'inventario;

- **apri** + **container/porta/oggetto nell'inventario**, consente di aprire un container o una porta all'interno di una stanza oppure un oggetto dell'inventario, se si ha la chiave specifica (non c'è bisogno di specificare la chiave). Se l'oggetto si trova nell'inventario tutto il suo contenuto verrà trasferito nell'inventario e l'oggetto distrutto;

- **prendi** + **oggetto/tutto/tutto eccetto (una lista di oggetti separati da una virgola)** consente di raccogliere un oggetto presente nella stanza (se è raccoglibile) o in un container (se è aperto). E' possibile specificare la parola **tutto** se si vuole raccogliere tutti gli oggetti presenti nella stanza o in un container, scrivendo il comando *prendi tutto* oppure se non si vogliono raccogliere degli oggetti è possibile specificare quali mediante la parola **eccetto** ad esempio *raccogli tutto eccetto la chiave magnetica e il fascicolo* oppure *raccogli tutto eccetto la chiave, il fascicolo, il cofanetto*;

- **usa** + **oggetto** consente di avere un'interazione personalizzata e scriptata con oggetti specifici;

- **parla**+**npc** consente di avviare una finestra di dialogo con un npc, se è comprensibile, e di ottenere eventualmente degli oggetti se l'npc li possiede;

- **dai** + **oggetto** + **npc** consente di dare un oggetto ad un npc, triggerando un'interazone scriptata;

- **esci** (con rispettivi alias), consente di terminare il gioco;

- **uccidi** + **npc** consente di uccidere un npc, se quest'ultimo non è importante ai fini della storia;

- **aiuto** consente di stampare a video una guida rapida ai comandi;

- **insulti vari** una piccola citazione triggerata da un insulto;

E' possibile utilizzare articoli o preposizioni, virgole e congiunzioni.
