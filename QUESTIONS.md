# PARTE TEORICA

### Arquitecturas de UI: MVP, MVVM y MVI

#### MVVM

##### ¿En qué consiste esta arquitectura?

A primera vista, MVVM (Modelo Vista Modelo-de-Vista) se parece mucho a los patrones de arquitectura MVP (Modelo Vista Presentador) y MVC (Modelo Vista Controlador). La principal diferencia entre MVVM y esos patrones es que hay un fuerte énfasis en que ViewModel no debe contener ninguna referencia a Vistas. El ViewModel solo proporciona información y no le interesa lo que la consume. Esto hace fácil de establecer una relación en la que sus Vistas pueden solicitar información desde cualquier ViewModel que necesiten.

MVVM se caracteriza por tratar de desacoplar lo máximo posible la interfaz de usuario de la lógica de la aplicación
También es de destacar con respecto al patrón de arquitectura MVVM que ViewModel también es responsable de exponer los eventos que las Vistas pueden observar.
MVVM se compone de las siguientes capas:

**Model**
El Modelo, más conocido como DataModel, se encarga de exponer datos relevantes a su ViewModels de una manera fácil de consumir. También debe recibir cualquier evento del ViewModel que necesita para crear, leer, actualizar o eliminar cualquier dato necesario del backend.

**ViewModel**
ViewModel recupera la información necesaria del modelo, aplica las operaciones necesarias y expone cualquier dato relevante para las Vistas. Es el responsable de envolver el modelo y prepara los datos observables necesitados por la vista. También proporciona enlaces a la vista para pasarle eventos al modelo. Los cambios en el ViewModel cambian automáticamente la vista y viceversa.

**View**
La Vista es con lo que la mayoría de nosotros ya estamos familiarizados, y es el único componente con el que el usuario final realmente interactúa. La Vista es responsable de mostrar la interfaz, y generalmente se representa en Android como Actividades o Fragmentos. Su papel principal en el patrón MVVM es observar uno o más ViewModels para obtener la información que necesita y actualizar la interfaz de usuario en consecuencia.

La Vista también informa a ViewModels sobre las acciones del usuario. Esto facilita la comunicación con más de un modelo. Las vistas pueden tener una referencia a uno o más ViewModels, pero ViewModels nunca puede tener información sobre las Vistas.

En Android, normalmente comunicará los datos entre las Vistas y ViewModels con Observables, usando bibliotecas como RxJava, LiveData o DataBinding.

##### ¿Cuáles son sus ventajas?
-	Proporciona un código más limpio y más legible.
-	Facilita que una aplicación que pueda ser expandida y mantenida de manera más sencilla, ya que al utilizar MVVM se programa por capas.
-	Permite realizar test unitarios y de interfaz de usuario de una manera sencilla, puesto que el código está separado.
-	Proporciona una aplicación más robusta.

##### ¿Qué inconvenientes tiene?
El principal inconveniente de este patrón de arquitectura es que puede ser demasiado complejo para aplicaciones cuya interfaz de usuario es bastante simple. Añadiendo tanto nivel de abstracción en tales aplicaciones pueden resultar en un código extenso que solo hace que la lógica subyacente sea más complicada.

#### MVP

##### ¿En qué consiste esta arquitectura?
La arquitectura MVP (Modelo Vista Presentador) viene a ser un derivado del MVC, que nos permite separar de forma muy clara nuestras vistas de la lógica de nuestras aplicaciones. Además es uno de los patrones más populares en el desarrollo nativo de Android.
En MVP el presentador asume la funcionalidad de presentación, donde toda su lógica al presentador. Es decir, el patrón MVP permite separar la capa de presentación de la lógica, para que todo sobre cómo funciona la interfaz de usuario sea independiente de cómo lo representamos en la pantalla. Idealmente, el patrón MVP lograría que la misma lógica pudiera tener vistas completamente diferentes e intercambiables.
MVP se compone de las siguientes partes:

**Model**
En una aplicación con una buena arquitectura en capas, este modelo solo sería la puerta de entrada a la capa de dominio o lógica empresarial. Sería como el proveedor de los datos que queremos mostrar en la vista.

**View**
La Vista, generalmente implementada por una Activity o Fragment, contendrá una referencia al presentador. Lo único que hará la vista es llamar a un método desde el Presentador cada vez que haya una acción de interfaz.

**Presenter**
El presentador es responsable de actuar como intermediario entre View y Model. Recupera datos del modelo y los devuelve formateados a la vista. Pero a diferencia del MVC típico, también decide qué sucede cuando interactúas con la Vista.

##### ¿Cuáles son sus ventajas?
-	El uso de esta arquitectura lleva a que cualquier persona pueda fácilmente mejorar, actualizar, modificar o arreglar cualquier parte de la aplicación.
-	Hace que sea más sencillo evitar que nuestras actividades terminen degradando en clases muy acopladas que consisten en cientos o incluso miles de líneas. En aplicaciones grandes, es esencial organizar bien nuestro código. De lo contrario, se hace imposible mantenerlas y extenderlas.
-	Con este patrón, el modelo y el presentador ahora se pueden testear para probar que se comportan como se esperaba en la unidad pruebas.

##### ¿Qué inconvenientes tiene?
No se debe olvidar que el presentador está conectado a la vista para siempre. Y la vista es una actividad, lo que significa que:
-	Podemos producir un leak de la actividad si ejecutamos tareas largas en segundo plano.
-	Podemos intentar actualizar una activity que ya haya muerto.

#### MVI

##### ¿En qué consiste esta arquitectura?
MVI son las siglas de Model-View-Intent. MVI es uno de los patrones de arquitectura más nuevos para Android. La arquitectura se inspiró en la naturaleza unidireccional y cíclica del Cycle.js y fue introducida por Hannes Dorfaman.
MVI funciona de una forma diferente a sus parientes MVC, MVP o MVVM, ya que introduce dos conceptos nuevos: la intención y el estado. La intención es un evento enviado al modelo de vista por la vista para realizar una tarea en particular. Puede ser activado por el usuario o por otras partes de su aplicación. Como resultado de eso, se establece un nuevo estado en ViewModel que a su vez actualiza la interfaz de usuario. En la arquitectura MVI, View escucha el estado. Cada vez que cambia el estado, se notifica a la Vista.
El papel de cada uno de sus componentes es el siguiente:

**Model**
Representa un estado. Los modelos en MVI deben ser inmutables para garantizar un flujo de datos unidireccional entre ellos y las otras capas de su arquitectura.

**Intent**
Representa una intención o un deseo de realizar una acción por parte del usuario. Por cada acción del usuario, la Vista recibirá una intención, que será observada por el Presentador y traducida a un nuevo estado en sus Modelos.

**View**
Al igual que en MVP, están representadas por interfaces, que luego son implementadas en una o más Actividades o Fragmentos.

##### ¿Cuáles son sus ventajas?
-	Un flujo de datos unidireccional y cíclico para su aplicación.
-	Estado coherente durante todo el ciclo de vida de sus vistas.
-	Modelos inmutables que proporcionan un comportamiento confiable y seguridad de subprocesos en aplicaciones grandes.
-	Mejora la capacidad de prueba de su aplicación al proporcionar estados predecibles y comprobables.

##### ¿Qué inconvenientes tiene?
-	Conduce a una gran cantidad de código repetitivo, ya que tenemos que mantener un estado para cada acción del usuario.
-	Hay que crear objetos para todos los estados. Esto hace que sea demasiado costoso para la gestión de la memoria de la aplicación.
-	La curva de aprendizaje de este patrón tiende a ser un poco más alta ya que se necesita tener conocimientos de otros temas intermedios-avanzados como la programación reactiva, multi-threading y RxJava.

---

### Testing

#### ¿Qué tipo de tests se deberían incluir en cada parte de la pirámide de test? Pon ejemplos de librerías de testing para cada una de las partes. 
La pirámide de test incluye las tres categorías de pruebas que se deben incluir en el conjunto de pruebas de una app:
-	Las pruebas de nivel inferior son pruebas de unidades que validan el comportamiento de la app de a una clase por vez.
Se trata de pruebas pequeñas llamadas pruebas unitarias que se ejecutan independientemente de un emulador o dispositivo físico y generalmente se enfocan en un solo componente, ya que se prueban todas sus dependencias de antemano y se burlan o tachan con el comportamiento deseado.
Son más rápidas que otras pruebas de la pirámide porque no requieren un emulador o dispositivo físico para ejecutarse.
En Android, las herramientas más utilizadas para estas pruebas son JUnit y Mockito.
-	Las pruebas de nivel intermedio son pruebas de integración que validan las interacciones entre los niveles de la pila dentro de un módulo o las interacciones entre módulos relacionados.
Estas pruebas ayudan a comprobar cómo interactúa el código con otras partes del marco de Android. Las pruebas medianas generalmente se ejecutan después de las pruebas unitarias completas sobre los componentes. Ahí es cuando se necesita verificar que las cosas funcionan correctamente de manera conjunta.
En Android, una de las herramientas más comunes para realizar pruebas de integración es Roboelectric.
-	Las pruebas de nivel superior son pruebas de extremo a extremo que validan la trayectoria de los usuarios en varios módulos de la app. Es decir, se trata de pruebas grandes abarcando pruebas de integración y de IU, que emulan el comportamiento del usuario y afirman los resultados de la IU.
Estas pruebas son las más lentas y caras porque requieren de emulador o de un dispositivo físico.
En Android, las herramientas más utilizadas para las pruebas de IU son Espresso y UI Automator.

En general, Google recomienda que cree pruebas de cada categoría en función de los casos de uso y de acuerdo con la siguiente regla:
70 por ciento pequeño, 20 por ciento mediano y 10 por ciento grande.

#### ¿Por qué los desarrolladores deben centrarse sobre todo en los Unido Tests?
Los desarrolladores se centran sobre todo en las pruebas unitarias debido a que:
-	Son mucho más rápidas de ejecutar que las pruebas de integración o de interfaz de usuario UI y además, las pruebas de integración generalmente se ejecutan en un emulador y al iniciar la aplicación en un emulador, simular la entrada del usuario lleva mucho más tiempo que solo ejecutar pruebas unitarias en la JVM local.
-	Las pruebas unitarias no requieren bibliotecas de pruebas de Android, ya que se está probando código regular de Java / Kotlin. Como resultado, usar JUnit, el marco de prueba para Java, es todo lo que se necesita para escribir pruebas unitarias.
-	Las pruebas unitarias constituyen la base del conjunto de pruebas de una aplicación. Es decir, si se puede probar que todas las unidades individuales de código y como resultado funcionan como se esperaba, se puede tener una gran confianza en que la aplicación en su conjunto también funcione como se esperaba.


---

### Inyección de dependencias

#### Explica en qué consiste y por qué nos ayuda a mejorar nuestro código.
Una dependencia ocurre cuando un objeto de una clase requiere un objeto de otra clase para funcionar correctamente. Estas dependencias son generalmente variables miembro de la clase. Por ejemplo: una sala de cine requiere al menos una pantalla, un proyector y una película, o de lo contrario, no funcionará.
Para evitar problemas en los que se genere una situación en la que la clase está estrechamente acoplada con sus dependencias, se debe evitar que dicha clase sea responsable tanto de crear como de usar sus dependencias. Por lo tanto, en lugar de que una clase cree sus propias dependencias, por ejemplo en el bloque inicializador, se deben crear dichas dependencias externas y pasarlas a la clase a través de su constructor. La inyección de dependencias es un patrón en el que las dependencias se pasan a una clase desde una entidad externa.

Hacer un buen uso de la inyección de dependencias ayudará a mejorar nuestro código, ya que:
-	La inyección de dependencias en una clase permite una mayor reutilización de esa clase.
-	La inyección de dependencia es especialmente importante para las pruebas unitarias de una clase, ya que pasar dependencias a través del constructor de una clase, permite que los objetos simulados se pasen a esa clase durante las pruebas unitarias.


#### Explica cómo se hace para aplicar inyección de dependencias de forma manual a un proyecto (sin utilizar librerías externas).
La forma más común de inyectar dependencias es a través del constructor. Siguiendo con el ejemplo enunciado en el punto anterior de la pantalla de cine, tendríamos:

class MovieTheatre(val screen: Screen, val projector: Projector, val movie: Movie) {
   fun playMovie() {
      System.out.println("Playing movie now")
   }
}

De esta manera, MovieTheatre ya no es responsable de crear sus propias dependencias; en su lugar recibe sus dependencias como parámetros cuando se construye, por ejemplo instanciándose en MainActivity.

Inyectar dependencias a través del constructor está bien para aplicaciones simples, sin embargo, una aplicación más complicada puede tener una red de dependencias, lo que conllevaría a una gran cantidad de código repetitivo.
Si una aplicación tiene dependencias demasiado complicadas, se puede considerar usar un marco de terceros como Koin, Proton, Feather, Tiger, Lightsaber, Transfundir o Daga 2.
