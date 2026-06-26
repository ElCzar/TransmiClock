<p align="center">
  <img src="logo.svg" alt="TransmiClock" width="120"/>
</p>

# ¿Qué es TransmiClock?
TransmiClock es una aplicación de código abierto para Wear OS que permite consultar en tiempo real los buses disponibles en cualquier parada de Bogotá, directamente desde tu reloj inteligente, sin necesidad de sacar el celular.

## Capturas de pantalla
> 🚧 Próximamente

---

# Requisitos
- Reloj inteligente con **Wear OS 3.0 o superior** (API 30+)
- [OPCIONAL] Aplicación **Wear Installer 2** instalada en el teléfono acompañante para la instalación de la aplicación

---

# Aviso legal
Esta aplicación ***NO*** está afiliada ni es respaldada oficialmente por TransMilenio S.A. ni por la Secretaría de Movilidad de Bogotá.

Hace uso de APIs internas descubiertas mediante ingeniería inversa de la aplicación oficial de TransMilenio. El uso de estas APIs es bajo su propia responsabilidad.

Los endpoints de dichas APIs no están incluidos en este repositorio. Sin embargo, los [releases oficiales](https://github.com/elczar/TransmiClock/releases) incluyen los secrets compilados y no requieren configuración adicional para su uso.

---

# Instalación

## Descarga
La forma más sencilla de instalar TransmiClock es descargar el APK firmado desde los [releases oficiales](https://github.com/elczar/TransmiClock/releases) e instalarlo mediante ADB.

## Instalación mediante ADB

### 1. Instalar ADB

<details>
<summary>Linux</summary>

```bash
# Debian/Ubuntu
sudo apt install adb

# Arch/CachyOS
sudo pacman -S android-tools

# Fedora/RHEL/CentOS
sudo dnf install android-tools
```

</details>

<details>
<summary>Windows</summary>

Descarga el [SDK Platform Tools](https://developer.android.com/studio/releases/platform-tools) de Android y agrégalo al PATH.

</details>

<details>
<summary>macOS</summary>

```bash
brew install android-platform-tools
```

</details>

### 2. Activar la depuración en el reloj

1. En el reloj, ve a **Ajustes → Acerca del reloj** y toca el **número de compilación** 7 veces para activar el modo desarrollador.
2. Ve a **Ajustes → Opciones de desarrollador** y activa:
   - **Depuración por ADB**
   - **Depuración inalámbrica**
3. Dentro de **Depuración inalámbrica**, selecciona **Emparejar dispositivo con código de emparejamiento** y toma nota del **código**, la **dirección IP** y el **puerto de emparejamiento**.

### 3. Conectar el reloj

```bash
# Emparejar (solo la primera vez)
adb pair <IP>:<PUERTO_EMPAREJAMIENTO>
# Introduce el código cuando se solicite

# Conectar (en la pantalla principal de depuración inalámbrica aparece la IP y puerto de conexión)
adb connect <IP>:<PUERTO_CONEXIÓN>
```

### 4. Instalar el APK

```bash
adb install transmiclock-signed.apk
```

---

# Colaboraciones

¿Quieres contribuir al proyecto? ¡Bienvenido!

- 🐛 **Reportar un problema** — Abre un [issue en GitHub](https://github.com/ElCzar/TransmiClock/issues).
- 🔧 **Contribuir código** — Haz un fork del repositorio y abre un Pull Request con tus cambios.
- 📬 **Contacto directo** — Si tienes alguna duda o sugerencia, puedes escribirme a [cesarandresolarte@gmail.com](mailto:cesarandresolarte@gmail.com).

## Compilación de la aplicación

Como se menciona en el aviso legal, las variables secretas (endpoints de la API) no se distribuyen en este repositorio. Si cuentas con dichas variables, encontrarás un archivo de ejemplo llamado `secrets.default.properties` con todas las propiedades necesarias. Para compilar:

1. Copia el archivo y renómbralo:
```bash
   cp secrets.default.properties secrets.properties
```
2. Rellena los valores en `secrets.properties`.
3. Compila normalmente con Gradle:
```bash
   ./gradlew assembleRelease
```

---

*TransmiClock es software libre distribuido bajo la licencia [GPL v3](LICENSE).*
