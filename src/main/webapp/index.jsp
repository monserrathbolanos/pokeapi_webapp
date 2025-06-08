<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mundo Pokemon</title>
</head>
<body>
    <h1>Detalles del Pokemon</h1>
    <div>
        <form action="<%= request.getContextPath() %>/pokemon" method="get">
            <label for="pokemon">Buscar Pokemon:</label>
            <input type="text" id="pokemon" name="pokemon">
            <button type="submit">Buscar</button>
        </form>

        <c:if test="${not empty pokeData}">
            <h2>${pokeData['name']}</h2> <!-- Acceder al nombre del Pokémon -->
            <img src="${pokeData['sprites']['front_default']}" alt="${pokeData['name']}"> <!-- Acceder a la imagen -->
            <p>Altura: ${pokeData['height']}</p> <!-- Acceder a la altura -->
            <p>Peso: ${pokeData['weight']}</p> <!-- Acceder al peso -->
        </c:if>
    </div>
</body>
</html>