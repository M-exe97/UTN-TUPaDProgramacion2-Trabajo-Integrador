# Food Store - Sistema de Gestion de Pedidos en Consola

## Descripcion del Proyecto
Este proyecto es una aplicacion de consola desarrollada en Java para la materia **Programación 2** de la *Tecnicatura Universitaria en Programacion (UTN)*. El sistema implementa un entorno CRUD completo para la gestión de Categorias, Productos, Usuarios y Pedidos de un negocio de comidas, opera con colecciones en memoria y aplicando conceptos de Programacion Orientada a Objetos (POO)

## Requisitos del Sistema
* **Lenguaje:** Java 21
* **Gestor de Dependencias / IDE:** Apache NetBeans (o entorno compatible con proyectos Java)
* **Almacenamiento:** En memoria (Java Collections Framework usando `ArrayList`)

## Estructura de Paquetes (Arquitectura en Capas)
El codigo fuente esta modularizado para poder garantizar la separacion de responsabilidades:
* `integrador.prog2.entities`: Tiene el modelo de dominio y la clase abstracta base de la jerarquia de herencia (Base, Categoria, DetallePedido, Pedido, Producto, Usuario)
* `integrador.prog2.enums`: Se encarga de las enumeraciones que definen los estados y opciones del sistema (Estado, FormaPago, Rol)
* `integrador.prog2.interfaces`: Define los contratos de comportamiento comun (Calculable)
* `integrador.prog2.services`: Es la capa de logica de negocio, validaciones comerciales y gestion del almacenamiento en memoria
* `integrador.prog2.exception`: Contiene las excepciones personalizadas controladas para el manejo de errores del negocio
* `integrador.prog2`: Contiene la clase Main que gestiona la interfaz de usuario por consola

## Instrucciones de Ejecucion
1. Clona el repositorio
2. Abri el proyecto en NetBeans configurado con el JDK 21
3. Ejecuta la clase `Main.java` que esta en el paquete raiz
4. Interactua con el sistema a traves de los menus numericos desplegados en la consola

## Documentacion
* **Video Demostrativo (10 min):** [Video en YouTube](https://youtu.be/WV6nPd0Pjwg)
* **Documentacion (PDF):** [Abrir PDF](https://github.com/M-exe97/UTN-TUPaDProgramacion2-Trabajo-Integrador/blob/master/Martinez_Exequiel_TPI.pdf)
