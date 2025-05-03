

# Document-workspace

## Introducción
Document Workspace es una aplicación web completa para la creación, organización y edición colaborativa de documentos y carpetas.     
Está diseñada para ofrecer una experiencia intuitiva y eficiente, permitiendo a los usuarios trabajar en equipo dentro de un entorno estructurado y moderno.

El proyecto está dividido en dos partes principales:
- Backend: Un servicio REST desarrollado en Spring Boot, responsable de gestionar la lógica de negocio, persistencia de datos y autenticación de usuarios.
- Frontend: Una aplicación Angular escrita en TypeScript que proporciona una interfaz limpia y dinámica, permitiendo a los usuarios interactuar fácilmente con el sistema.

Además, el sistema se integra con OnlyOffice, una potente suite ofimática online, para ofrecer edición de documentos colaborativa en tiempo real directamente desde la plataforma.

## Características

- 📄 **Gestión de documentos:** creación, edición y eliminación de archivos desde una interfaz intuitiva.

- 📁 **Organización por carpetas:** estructuración de documentos en espacios de trabajo organizados.

- ⬆️ **Subida y descarga de documentos:** subida de archivos mediante un dropzone y posterior descarga.

- 👥 **Colaboración en tiempo real:** edición en tiempo real de documentos mediante integración con OnlyOffice.

- 🔐 **Autenticación de usuarios:** control de acceso mediante un sistema de autenticación seguro.

- 🌐 **Interfaz web moderna:** diseño responsive y fluido para adaptarse a diferentes tipos de pantalla.

- 🚀 **Arquitectura modular:** separación clara entre frontend y backend para facilitar el mantenimiento y escalabilidad.

## Teconolgías

-   **Spring Boot:** Framework principal para el desarrollo del backend.

-    **Angular:** Framework utilizado para desarrollar el frontend de la aplicación.

-   **MySQL:** Base de datos relacional para almacenar información.

-   **Docker:** Creación y uso de contenedores para facilitar el despliegue.

-   **OnlyOffice:** Integración para la edición colaborativa de documentos.

-   **Lombok:** Reducción del código mediante anotaciones para simplificar el desarrollo en Java.

## Requisitos

### Backend

-   **Java 23**: Versión necesaria para ejecutar el backend desarrollado con Spring Boot.

-   **Maven**: Herramienta de construcción y gestión de dependencias para el proyecto backend.

### Frontend
-   **Angular 18.2.12**: Framework utilizado para el desarrollo del frontend.

-   **Node.js**: Plataforma de ejecución de JavaScript para el frontend.

-   **npm**: Gestor de paquetes para instalar las dependencias del frontend.

### Otros

-   **Docker**: Utilizado para la creación y gestión de contenedores para el despliegue del proyecto.