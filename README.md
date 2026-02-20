PROYECTO: EstacionaFácil App
ALUMNO: Felipe Guerrero Muñoz
ASIGNATURA: Desarrollo de Aplicaciones Móviles

=========================================================================
PARTE 1: IMPLEMENTACIÓN SEMANA 6 (ARQUITECTURA Y COMPONENTES)
=========================================================================

En esta entrega se aplicó una arquitectura modular separando la Lógica de Datos (Content Providers) de la Interfaz de Usuario (Fragments/Screens), cumpliendo con los contenidos de la semana.

1. CONTENT PROVIDERS (GESTIÓN DE DATOS):
   Se implementaron objetos Singleton ("Providers") que actúan como gestores de datos centralizados, simulando la persistencia y lógica de negocio (CRUD):
   - Ubicación: com/example/estacionamientofacilapp/data/
     * UsuariosProvider.kt: Gestiona autenticación, roles y flujo de aprobación de solicitudes.
     * ResidentesProvider.kt: Gestiona la base de datos de propietarios y departamentos.
     * VehiculosEspecialesProvider.kt: Gestiona permisos temporales (Ambulancias, Furgones, Proveedores).

2. FRAGMENTS / SCREENS (VISTAS MODULARES):
   Se desarrollaron pantallas independientes y reutilizables para cada módulo de gestión:
   - Ubicación: com/example/estacionamientofacilapp/ui/screens/
     * UsuariosScreen.kt: Panel de administración para aprobar/rechazar solicitudes de acceso.
     * ResidentesScreen.kt: ABM (Alta/Baja/Modificación) de residentes.
     * VehiculosEspecialesScreen.kt: Interfaz dinámica con tarjetas y lógica visual (Iconos cambiantes).

3. LÓGICA DE NEGOCIO AVANZADA:
   - Flujo de Aprobación: El registro (RegisterScreen) no crea usuarios activos automáticamente, sino "Solicitudes". El Admin debe aprobarlas manualmente en UsuariosScreen.
   - UI Dinámica: En "Vehículos Especiales", las tarjetas cambian de color e ícono automáticamente según el tipo de vehículo (Escolar/Proveedor/Emergencia).

=========================================================================
PARTE 2: CORRECCIONES SUMATIVA ANTERIOR (KOTLIN AVANZADO)
=========================================================================

Se han incorporado las funcionalidades de Kotlin solicitadas en el feedback anterior.
Para facilitar su revisión, se centralizaron en un archivo específico:

UBICACIÓN: com/example/estacionamientofacilapp/utils/FuncLambdasFaltantes.kt

DETALLE DE IMPLEMENTACIÓN:

1. PROPIEDAD DE EXTENSIÓN:
   - Código: val String.esPatenteValida
   - Uso: Valida la longitud de la patente en el diálogo de ingreso (ParkingListScreen).

2. FUNCIÓN INLINE PROPIA:
   - Código: inline fun ejecutarSeguro(...)
   - Uso: Reemplaza los bloques try-catch repetitivos al formatear textos en las tarjetas (ParkingCard).

3. LAMBDA CON ETIQUETA (Labeled Lambda):
   - Código: fun validarCamposObligatorios(...)
   - Uso: Usa la etiqueta 'validacion@' para controlar el flujo al revisar campos vacíos.
