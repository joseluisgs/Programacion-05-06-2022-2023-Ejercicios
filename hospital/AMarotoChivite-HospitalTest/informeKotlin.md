# Datos alumno

- Nombre: Angel Maroto Chivite
- Email Luis Vives: angel.maroto@alumno.iesluisvives.org
- GitHub Username: Sbytmacke

&nbsp;

## 1. Diseño implementado

He empleado KOTLIN y Gradle con las dependencias de JUNIT y Mockito.

Hay tres repositorios donde:

- El primero se ha realizado con listas
- El segundo con conjuntos (TreeSet)
- El tercero con mapas (TreeMap)

He utilizado una clase abstracta en los repositoryTest para no repetir código, ya que los tres respositorios implementan
las mismas funciones devolviendo los mismos tipos de valores

Preparo una lista de repositorios mockeados

![](imagenesKotlin/repos.png)

Deberemos mediante la función "patientController.setTypeRepo(PatientController.Type.Repo)" elegir el tipo de repositorio, que manipular.

![](imagenesKotlin/list.png)

![](imagenesKotlin/set.png)

![](imagenesKotlin/map.png)

---

## 3. Covertura Final

87% de todo el código está probado

![](imagenesKotlin/cobertura.png)
