# Plataforma de Reportes Ciudadanos

## Descripción  
La seguridad ciudadana y el bienestar comunitario son preocupaciones crecientes en muchas ciudades del mundo. Esta plataforma permite a los ciudadanos reportar situaciones de riesgo o emergencia de manera sencilla y eficiente, facilitando la gestión de incidentes por parte de moderadores y promoviendo una comunidad más segura y colaborativa.  

## Tecnologías Utilizadas  
- **Lenguaje:** Kotlin  
- **Framework UI:** Jetpack Compose  
- **Autenticación y Notificaciones:** Firebase (Firebase Authentication y Firebase Cloud Messaging)  
- **Mapas y Geolocalización:** Google Maps o Mapbox  
- **Almacenamiento de Imágenes:** Cloudinary, AWS S3 o Google Cloud Storage  
- **Control de Versiones:** GitHub  

## Características Principales  

### Cliente  
- Registro e inicio de sesión.  
- Crear, editar y eliminar reportes.  
- Marcar reportes como resueltos.  
- Priorizar reportes con la opción "Es importante".  
- Comentar reportes de la comunidad.  
- Recibir notificaciones en tiempo real sobre reportes en su área.  
- Editar su perfil y eliminar su cuenta.  
- Recuperación de contraseña mediante correo electrónico.  

### Moderador/Administrador  
- Iniciar sesión como moderador.  
- Gestionar reportes (verificar, rechazar, eliminar).  
- Indicar razones de rechazo y permitir modificaciones en un plazo de 5 días.  
- Marcar reportes como resueltos.  
- Gestionar su cuenta (editar, eliminar).  

## Categorías de Reportes  
1. **Seguridad:** Robos, actividades delictivas, vandalismo.  
2. **Emergencias Médicas:** Accidentes de tránsito, desmayos, urgencias médicas.  
3. **Infraestructura:** Calles en mal estado, problemas de alumbrado público.  
4. **Mascotas:** Mascotas perdidas o encontradas.  
5. **Comunidad:** Contaminación, basuras, problemas ambientales.  

## Requisitos del Proyecto  
- **Fase 1:** Diseño de prototipos siguiendo Material Design.  
- **Fase 2:** Implementación de UI y navegación sin persistencia de datos.  
- **Fase 3:** Implementación completa con persistencia, autenticación, geolocalización y subida de imágenes.  
