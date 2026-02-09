# Literalura

Aplicación de consola desarrollada en **Java + Spring Boot** que consume la API pública **Gutendex** para buscar libros y autores, almacenarlos en una base de datos y consultarlos mediante diferentes opciones.

Proyecto realizado como parte del **Challenge Literalura – Alura Latam**.

---

## Funcionalidades

La aplicación ofrece un menú interactivo con las siguientes opciones:

1. **Buscar libro por título**
   - Consulta la API de Gutendex
   - Guarda el libro y su autor en la base de datos si no existen

2. **Listar libros registrados**
   - Muestra todos los libros almacenados en la base de datos

3. **Listar autores registrados**
   - Muestra los autores guardados junto con sus datos

4. **Listar autores vivos en un año específico**
   - Filtra autores que estaban vivos en el año ingresado

5. **Listar libros por idioma**
   - Permite buscar libros por idioma (`es`, `en`, `fr`, etc.)

0. ❌ **Salir del programa**

---

## Tecnologías utilizadas

- **Java 21**
- **Spring Boot 4**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Jackson (JSON)**
- **API Gutendex**
- **Maven**

---

## 🧩 Estructura del proyecto

