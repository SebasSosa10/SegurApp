# SegurApp 📱

**SegurApp** es una aplicación móvil desarrollada con **Kotlin** y **Jetpack Compose** en **Android Studio**, diseñada para mejorar la **seguridad ciudadana** y el **bienestar comunitario**. Permite a los usuarios reportar situaciones de riesgo o emergencias en tiempo real, visualizar alertas cercanas y colaborar con su comunidad.

---

## 🚀 Funcionalidades

## 👤 Características principales

### Cliente
- Registro y autenticación con correo y contraseña.
- Reportar incidentes con título, categoría, descripción, ubicación (latitud y longitud) y al menos una imagen.
- Categorías de reporte:
  - Seguridad (robos, actividades delictivas)
  - Emergencias médicas (accidentes, desmayos)
  - Infraestructura (calles en mal estado, alumbrado)
  - Mascotas (perdidas o encontradas)
  - Comunidad (basura, contaminación)
- Visualización de reportes en tiempo real.
- Calificación de reportes como importantes.
- Cambiar el estado de un reporte a "Resuelto".
- Agregar comentarios con notificación al autor del reporte.
- Notificaciones relevantes según ubicación (Firebase Cloud Messaging).
- Gestión de cuenta personal (editar, eliminar).
- Recuperación de contraseña vía email.

### 🛡️ Moderador / Administrador
- Autenticación.
- Gestión de reportes: verificar, rechazar (con motivo), eliminar o marcar como resueltos.
- Gestión de cuenta propia (editar, eliminar).
- Moderadores precargados en la base de datos.

## 📌 Requisitos clave

- **Mapa interactivo** (Google Maps o Mapbox) para seleccionar la ubicación del reporte o residencia.
- Almacenamiento de coordenadas geográficas (latitud y longitud).
- **Notificaciones geolocalizadas**: sistema define un radio en kilómetros para determinar la relevancia de las alertas.
- Almacenamiento de imágenes en un **servicio externo** como Cloudinary o AWS S3.
- Prevención de registros duplicados.
- Recuperación de contraseñas mediante enlace enviado por correo electrónico.

## 🛠️ Tecnologías utilizadas

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose
- **Autenticación y notificaciones:** Firebase Authentication & Firebase Cloud Messaging
- **Mapas:** Google Maps SDK / Mapbox
- **Almacenamiento de imágenes:** Cloudinary / AWS S3 / GCP Storage
- **Control de versiones:** Git + GitHub
- **Internacionalización:** Idiomas múltiples (fase 3)

## 🧪 Fases del proyecto

| Fase | Descripción |
|------|-------------|
| 1️⃣   | Prototipos UI (Mockups) con lineamientos de Material Design |
| 2️⃣   | Desarrollo funcional con manejo de datos en memoria |
| 3️⃣   | Persistencia, autenticación con Firebase, imágenes, notificaciones y multilenguaje |

## ✨ Requisitos adicionales (necesidades del sistema)

- 🔔 Las notificaciones deben enviarse según cercanía geográfica del usuario.
- 📌 Se define un **radio en kilómetros** para determinar qué reportes son relevantes.
- 📧 Comentarios generan una notificación automática al autor del reporte.
- 📸 Las imágenes se suben a un servicio externo.
- 🔐 Recuperación de contraseña por email.
- ✅ Revisión de reportes incluye validación y opción de corrección en 5 días si fue rechazado.


## 👥 Equipo de desarrollo

| Nombre                         | Rol                   |
|--------------------------------|-----------------------|
| Joan Sebastián Sosa Bedoya     | Desarrollador         |
| Juan Diego Rojas Abarka        | Desarrollador         |
| Axel Derek Gutiérrez           | Desarrollador         |
