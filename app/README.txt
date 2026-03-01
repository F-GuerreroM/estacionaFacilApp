# PROYECTO: EstacionaFácil App - Versión 3.0 (FINAL)
**ALUMNO:** Felipe Guerrero Muñoz
**ASIGNATURA:** Desarrollo de Aplicaciones Móviles
**DOCENTE:** Pablo Vilches

=========================================================================
PARTE 1: IMPLEMENTACIÓN FINAL (SEMANA 8 - NUBE Y DISTRIBUCIÓN)
=========================================================================

En esta fase final, la aplicación ha evolucionado de un prototipo local a una solución empresarial real integrada con servicios en la nube y hardware.

1. BACKEND INTEGRADO (FIREBASE CLOUD):
   - Firebase Auth: Autenticación real de usuarios (Login/Registro/Recuperación).
   - Realtime Database: Sincronización en tiempo real de vehículos estacionados y base de datos de residentes.
   - Firebase App Distribution: La aplicación está publicada para su descarga y pruebas.

2. INTEGRACIÓN DE HARDWARE (GPS & MAPS):
   - Geolocalización: El Dashboard detecta las coordenadas del punto de control mediante el sensor GPS.
   - Google Maps Intent: Comunicación entre apps para visualizar la ubicación del estacionamiento.

3. CALIDAD DE SOFTWARE (UNIT TESTING):
   - Ubicación: app/src/test/java/com/example/estacionamientofacilapp/
   - Implementación de JUnit para validar la lógica de formateo de patentes y modelos de datos.

=========================================================================
PARTE 2: CREDENCIALES DE PRUEBA Y ACCESO
=========================================================================

Para facilitar la corrección, utilice las siguientes credenciales (ya creadas en Firebase):

- CORREO: profe@duoc.cl
- CONTRASEÑA: 123456

*Nota: También puede registrar un usuario nuevo desde la App; el sistema enviará los datos automáticamente a la consola de Firebase.*

=========================================================================
PARTE 3: GUÍA DE REVISIÓN PARA LA RÚBRICA (DÓNDE BUSCAR)
=========================================================================

Para validar los puntos exigidos en la rúbrica, consulte los siguientes archivos:

1. KOTLIN AVANZADO (Lambdas, Filtros, Extensiones):
   - Ubicación: com.example.estacionamientofacilapp.utils.FuncLambdasFaltantes.kt
   - Ubicación: com.example.estacionamientofacilapp.ui.screens.ParkingListScreen.kt (Uso de .filter { })

2. MANEJO DE ESTADOS KTX Y FIREBASE:
   - Ubicación: com.example.estacionamientofacilapp.data.ParkingProvider.kt (Escucha asíncrona de la DB).

3. PREPARACIÓN PARA PUBLICACIÓN (APK FIRMADO):
   - El archivo ejecutable se encuentra en la carpeta: /Ejecutable/app-release.apk
   - Se incluye el certificado Keystore en la documentación del informe técnico.

=========================================================================
PARTE 4: ESTRUCTURA DEL PROYECTO
=========================================================================

- /ui/screens/: Interfaces de usuario en Jetpack Compose de alto contraste.
- /data/: Proveedores de datos conectados a Firebase (CRUD).
- /utils/: Funciones de extensión, formateadores y gestión de seguridad.
- /navigation/: Sistema de rutas y NavHost de la aplicación.
