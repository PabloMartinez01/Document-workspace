

# Document-workspace

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Apache Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.io/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![OnlyOffice](https://img.shields.io/badge/OnlyOffice-00AEEF?style=for-the-badge&logo=onlyoffice&logoColor=white)](https://www.onlyoffice.com/)


## Introducción

Document Workspace es una aplicación web completa para la creación, organización y edición colaborativa de documentos y carpetas.     
Está diseñada para ofrecer una experiencia intuitiva y eficiente, permitiendo a los usuarios trabajar en equipo dentro de un entorno estructurado y moderno.

El proyecto está dividido en dos partes principales:
- Backend: Un servicio REST desarrollado en **Spring Boot**, responsable de gestionar la lógica de negocio, persistencia de datos y autenticación de usuarios.
- Frontend: Una aplicación **Angular** escrita en TypeScript que proporciona una interfaz limpia y dinámica, permitiendo a los usuarios interactuar fácilmente con el sistema.

Además, el sistema se integra con **OnlyOffice Document Server**, una potente suite ofimática online, para ofrecer edición de documentos colaborativa en tiempo real directamente desde la plataforma.

## Características

- 📄 **Gestión de documentos:** creación, edición y eliminación de archivos desde una interfaz intuitiva.

- 📁 **Organización por carpetas:** estructuración de documentos en espacios de trabajo organizados.

- ⬆️ **Subida y descarga de documentos:** subida de archivos mediante un dropzone y posterior descarga.

- 👥 **Colaboración en tiempo real:** edición en tiempo real de documentos mediante integración con **OnlyOffice Document Server**.

- 🔐 **Autenticación de usuarios:** control de acceso mediante un sistema de autenticación seguro.

- 🌐 **Interfaz web moderna:** diseño responsive y fluido para adaptarse a diferentes tipos de pantalla.

- 🚀 **Arquitectura modular:** separación clara entre frontend y backend para facilitar el mantenimiento y escalabilidad.

## Teconolgías

-   **Spring Boot:** Framework principal para el desarrollo del backend.

-    **Angular:** Framework utilizado para desarrollar el frontend de la aplicación.

-   **MySQL:** Base de datos relacional para almacenar información.

-   **Docker:** Creación y uso de contenedores para facilitar el despliegue.

-   **OnlyOffice Document Server:** Integración para la edición colaborativa de documentos.

-   **Tailwindcss**: Creación de interfaces modernas y responsive.

-   **Lombok:** Reducción del código mediante anotaciones para simplificar el desarrollo en Java.

## Requisitos

### Backend

-   **Java 23**: Versión necesaria para ejecutar el backend desarrollado con Spring Boot.

-   **Maven**: Herramienta de construcción y gestión de dependencias del proyecto


### Frontend

-   **Angular 18.2.12**: Framework utilizado para el desarrollo del frontend.

-   **Node.js**: Plataforma de ejecución de JavaScript.

-   **npm**: Gestor de paquetes para instalar las dependencias.

### Otros

-   **Docker**: Utilizado para la creación y gestión de contenedores para el despliegue del proyecto.

## Configuración y ejecución

### Clonación del repositorio

Clona el repositorio en tu máquina local utilizando Git:
```bash
git clone https://github.com/PabloMartinez01/Document-workspace.git
cd Document-workspace
```

### Configuración de Docker

Modifica el archivo [docker-compose.yml](./document-workspace-backend/docker-compose.yml) para configura los contenedores de **MySQL** y **OnlyOffice Document Server**

El servicio de base de datos se configura a través de las siguientes variables de entorno en el contenedor de **MySQL**:

```yaml
MYSQL_DATABASE: <database>
MYSQL_USER: <username>
MYSQL_PASSWORD: <password>
```

-   `MYSQL_DATABASE`: nombre de la base de datos que se creará al iniciar el contenedor.

-   `MYSQL_USER`: usuario con acceso a la base de datos.

-   `MYSQL_PASSWORD`: contraseña del usuario definido.

El contenedor de **OnlyOffice Document Server** puede configurarse para habilitar o deshabilitar la autenticación mediante JWT. Para ello, puedes ajustar los siguientes valores:

```properties
JWT_ENABLED=<enabled>
JWT_SECRET=<secret>
```

-   `JWT_ENABLED=true`: activa la verificación de tokens JWT entre el backend y OnlyOffice.

-   `JWT_SECRET`: define la clave secreta compartida para firmar y verificar los tokens.

> [!WARNING]
>Asegúrate de usar los mismos valores en la configuración del backend

### Ejecución de Docker Compose

Una vez configurado el archivo `docker-compose.yml` con los servicios necesarios (MySQL, backend, frontend y OnlyOffice), puedes levantar todo el entorno con el siguiente comando:

```bash
docker-compose up
```

### Configuración del backend

El backend requiere configurar ciertas propiedades para que funcione correctamente con los servicios externos. Estas pueden establecerse en el archivo [application.properties](./document-workspace-backend/src/main/resources/application.properties)

```properties
files.document-service=<document-service-url>
files.document-server=<document-server-url>

application.security.jwt.secret-key=<secret>
application.security.jwt.expiration=<expiration>
```

-   `files.document-service`: dirección base del backend, usada para generar URLs de acceso a los documentos.

-   `files.document-server`: dirección del servidor de **OnlyOffice Document Server** (ej. `http://localhost:8082` o una URL pública).

-   `application.security.jwt.secret-key`: clave secreta usada para firmar y verificar tokens JWT si está habilitado.

-   `application.security.jwt.expiration`: duración del token JWT en milisegundos (ej. `86400000` para 24 horas).

### Configuración del frontend

El archivo [environment.ts](./document-workspace-frontend/src/environments/environment.ts) debe configurarse con las direcciones necesarias para que el frontend se comunique con los servicios del backend y OnlyOffice.

```ts
documentServer: '<document-server-url>',
documentService: '<document-service-url>'
```

-   `documentServer`: URL del **OnlyOffice Document Server**

-   `documentService`: URL del backend que expone los servicios REST

### Compilación con Maven

El proyecto incluye un **pom** que permite compilar tanto el backend como el frontend desde un solo lugar.

En la raíz del proyecto (donde está el `pom.xml` principal), ejecuta:

```bash
mvn clean install
```

### Ejecución del backend

Para ejecutar el backend debes hacerlo desde su módulo.

1. Entra al directorio del backend:
```bash
cd document-workspace-backend
```
2. Ejecuta el siguiente comando:
```bash
mvn spring-boot:run
```

### Ejecución del frontend

Para ejecutar el frontend debes hacerlo desde su módulo.

1. Entra al directorio del frontend:
```bash
cd document-workspace-frontend
```
2. Ejecuta el siguiente comando:
```bash
npm start
```
