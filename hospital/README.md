# Hospiatal

Usando las tres colecciones: Listas, Conjuntos y Mapas

Crear la gestión de un hospital sabiendo su tamaño máximo. 
Operaciones del repositorio (debes indicar qué operaciones devileven nulos):
- ingresar: Paciente?
- alta: Paciente?
- estaCompleto: Boolean
- numeroPacientes: Int
- obtenertodosPacientes: List
- pacientePorDni: Paciente?
- pacientesOrdenadosFechaIngreso: List
- pacientesOrdenadosPorNombre: List
- pacientesPorTipo: List
- numPacientesPorTipo: Int

Paciente:
- dni
- nombre
- fechaNacimiento
- fechaIngreso
- fechaAlta

Testear los tres repositorios con JUnit

Crear un controlador que trabaje con cualquier de los tres repositorios y testearlo usando Mocks.Este controlador usa excepciones para los casos "excepcionales"
