PROYECTO: EstacionaFácil App
ALUMNO: Felipe Guerrero Muñoz
ASIGNATURA: Desarrollo de Aplicaciones Móviles

=========================================================================
PARTE 1: IMPLEMENTACIÓN SEMANA 6 (ARQUITECTURA Y COMPONENTES)
=========================================================================

En esta entrega se aplicó una arquitectura modular separando la Lógica de Datos (Content Providers) de la Interfaz de Usuario (Fragments/Screens).

1. GESTORES DE DATOS (ARQUITECTURA MODERNA):
   Se implementaron objetos Singleton que actúan como proveedores de datos centralizados:
   - Ubicación: com/example/estacionamientofacilapp/data/
     * UsuariosProvider.kt: Gestión de usuarios y aprobaciones.
     * ResidentesProvider.kt: Gestión de residentes.
     * VehiculosEspecialesProvider.kt: Gestión de permisos temporales.

2. VISTAS MODULARES (SCREENS):
   - Ubicación: com/example/estacionamientofacilapp/ui/screens/
     * UsuariosScreen.kt, ResidentesScreen.kt, VehiculosEspecialesScreen.kt.

=========================================================================
PARTE 2: MEJORA OPCIONAL SOLICITADA (CONTENT PROVIDER ESTÁNDAR)
=========================================================================

Siguiendo la recomendación de mejora del docente, se implementó un CONTENT PROVIDER NATIVO DE ANDROID para cumplir con el estándar técnico clásico, coexistiendo con la arquitectura moderna.

1. IMPLEMENTACIÓN:
   - Clase: com/example/estacionamientofacilapp/data/EstacionaFacilProvider.kt
   - Funcionalidad: Expone un cursor matricial (MatrixCursor) con un mensaje de estado del sistema.

2. REGISTRO:
   - Archivo: AndroidManifest.xml
   - Authority: com.example.estacionamientofacilapp.provider

3. VERIFICACIÓN (PRUEBA TÉCNICA):
   - Al iniciar la pantalla de LOGIN, la app realiza una consulta real mediante URI ("content://...") al Provider.
   - EVIDENCIA EN LOGCAT: Busque el tag "System.out" o el mensaje:
     "ESTACIONAFACIL PROVIDER TEST: ContentProvider Nativo: OK"

=========================================================================
PARTE 3: CORRECCIONES SUMATIVA ANTERIOR (KOTLIN AVANZADO)
=========================================================================

Se mantienen las funcionalidades solicitadas en el feedback anterior:
UBICACIÓN: com/example/estacionamientofacilapp/utils/FuncLambdasFaltantes.kt

1. PROPIEDAD DE EXTENSIÓN (val String.esPatenteValida):
   - Uso: Validación de formato en ParkingListScreen.

2. FUNCIÓN INLINE PROPIA (inline fun ejecutarSeguro):
   - Uso: Reemplazo de try-catch en ParkingCard.

3. LAMBDA CON ETIQUETA (validarCamposObligatorios):
   - Uso: Control de flujo en formularios.
=========================================================================
PARTE 4: IMPLEMENTACIÓN SEMANA 7 (BACKEND CON FIREBASE)
=========================================================================

Para esta entrega se integró el framework Firebase de Google para gestionar
la persistencia de datos en la nube y la autorización, cumpliendo con los
requerimientos de la rúbrica (CRUD y Autenticación).

1. AUTENTICACIÓN (FIREBASE AUTH):
   Se implementó el acceso por Correo/Contraseña, reemplazando las validaciones locales.
   - Archivo Lógico: data/UsuariosProvider.kt (Métodos: login, registrar, recuperarClave)
   - Vistas Actualizadas:
     * LoginScreen.kt (Validación real asíncrona)
     * RegisterScreen.kt (Creación de cuentas en Firebase)
     * RecoverPasswordScreen.kt (Envío de correo de recuperación real)

2. BASE DE DATOS EN TIEMPO REAL (FIREBASE REALTIME DATABASE):
   Se implementaron operaciones CRUD (Crear, Leer, Eliminar) con listeners
   en tiempo real (ValueEventListener) para la gestión de datos.

   A) Módulo de Residentes:
      - Provider: data/ResidentesProvider.kt
      - Vista: ui/screens/ResidentesScreen.kt (Escucha cambios vía DisposableEffect)

   B) Módulo de Personal (Usuarios):
      - Provider: data/UsuariosProvider.kt (Sección Database)
      - Vista: ui/screens/UsuariosScreen.kt (Gestor de personal en la nube)

   C) Módulo de Vehículos Especiales (Permisos):
      - Provider: data/VehiculosEspecialesProvider.kt
      - Vista: ui/screens/VehiculosEspecialesScreen.kt

Nota: La aplicación es adaptativa y requiere conexión a internet para sincronizar
los datos de los Providers con la consola de Firebase. La tabla principal de
"Estacionados Actuales" se mantendrá en memoria temporal y será migrada a la
base de datos en la entrega final.