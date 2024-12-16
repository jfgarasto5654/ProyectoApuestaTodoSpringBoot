-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-12-2024 a las 05:13:14
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `apuestatodo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `apuesta`
--

CREATE TABLE `apuesta` (
  `id_apuesta` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `por_quien` varchar(255) DEFAULT NULL,
  `fk_id_resultado` int(11) DEFAULT NULL,
  `fk_id_usuario` int(11) DEFAULT NULL,
  `fk_id_partido` int(11) DEFAULT NULL,
  `estado` varchar(20) DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `apuesta`
--

INSERT INTO `apuesta` (`id_apuesta`, `monto`, `por_quien`, `fk_id_resultado`, `fk_id_usuario`, `fk_id_partido`, `estado`) VALUES
(61, 1.00, 'local', 1, 22, 1, 'N'),
(62, 1.00, 'local', 1, 22, 1, 'N'),
(63, 50.00, 'local', 1, 22, 1, 'N'),
(64, 50.00, 'local', 1, 22, 1, 'N'),
(65, 100.00, 'local', 1, 22, 1, 'N'),
(66, 50.00, 'local', 1, 22, 1, 'N'),
(67, 100.00, 'local', 1, 22, 1, 'N'),
(68, 100.00, 'local', 1, 22, 1, 'N'),
(69, 100.00, 'local', 1, 22, 1, 'N'),
(70, 50.00, 'local', 1, 22, 1, 'N'),
(71, 100.00, 'local', 1, 22, 1, 'N'),
(72, 100.00, 'local', 1, 22, 1, 'N'),
(73, 100.00, 'local', 1, 22, 1, 'N'),
(74, 100.00, 'local', 1, 22, 1, 'N'),
(75, 400.00, 'local', 1, 22, 1, 'N'),
(76, 200.00, 'local', 1, 22, 1, 'N'),
(77, 150.00, 'local', 1, 22, 1, 'N'),
(78, 1000.00, 'local', 1, 22, 1, 'N'),
(79, 1000.00, 'local', 1, 22, 1, 'N'),
(80, 1000.00, 'visitante', 1, 22, 1, 'N'),
(81, 400.00, 'local', 2, 1, 2, 'N'),
(82, 8000.00, 'visitante', 3, 1, 3, 'N'),
(83, 100.00, 'local', 2, 22, 2, 'N'),
(84, 100.00, 'local', 1, 22, 1, 'N'),
(85, 100.00, 'local', 1, 22, 1, 'N'),
(86, 100.00, 'local', 2, 22, 2, 'N'),
(87, 220.00, 'local', 4, 22, 4, 'N'),
(88, 400.00, 'local', 2, 22, 2, 'N'),
(89, 150.00, 'local', 4, 22, 4, 'N'),
(90, 100.00, 'local', 2, 22, 2, 'N'),
(91, 100.00, 'local', 3, 22, 3, 'N'),
(92, 400.00, 'local', 2, 22, 2, 'N'),
(93, 100.00, 'local', 3, 22, 3, 'N'),
(94, 100.00, 'local', 2, 22, 2, 'N'),
(95, 400.00, 'local', 1, 22, 1, 'N'),
(96, 200.00, 'visitante', 2, 22, 2, 'N'),
(99, 166.00, 'visitante', 2, 22, 2, 'G'),
(100, 400.00, 'local', 3, 22, 3, 'G'),
(103, 100.00, 'visitante', 1, 22, 22, 'G'),
(104, 100.00, 'visitante', 1, 22, 22, 'G');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partido`
--

CREATE TABLE `partido` (
  `id_partido` int(11) NOT NULL,
  `local` varchar(255) NOT NULL,
  `visitante` varchar(255) NOT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `balance` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `partido`
--

INSERT INTO `partido` (`id_partido`, `local`, `visitante`, `fecha`, `balance`) VALUES
(1, 'pendiente', 'pendiente', '2023-11-01', 0),
(2, 'Manchester City', 'Liverpool FC', '2023-11-02', 0),
(3, 'Bayern Munich', 'Borussia Dortmund', '2023-11-03', 0),
(4, 'Paris Saint-Germain', 'AC Milan', '2023-11-04', 0),
(5, 'Juventus', 'Inter Milan', '2023-11-05', 0),
(6, 'Chelsea FC', 'Arsenal FC', '2023-11-06', 0),
(7, 'Atletico Madrid', 'Sevilla FC', '2023-11-07', 0),
(8, 'Borussia Monchengladbach', 'Hertha Berlin', '2023-11-08', 0),
(9, 'Ajax Amsterdam', 'PSV Eindhoven', '2023-11-09', 0),
(10, 'AS Roma', 'SS Lazio', '2023-11-10', 0),
(11, 'FC Porto', 'SL Benfica', '2023-11-11', 0),
(12, 'Tottenham Hotspur', 'Manchester United', '2023-11-12', 0),
(13, 'ACF Fiorentina', 'Napoli', '2023-11-13', 0),
(14, 'Everton FC', 'Leeds United', '2023-11-14', 0),
(15, 'Ajax Cape Town', 'Kaizer Chiefs', '2023-11-15', 0),
(16, 'Boca', 'River', '2023-11-22', 0),
(17, 'Boca', 'River', '2023-11-21', 0),
(18, 'Atlanta', 'Chacarita', '2023-11-02', 0),
(19, 'Atlanta', 'Chacarita', '2023-11-07', 0),
(20, 'Atlanta', 'Chacarita', '2023-11-17', 0),
(21, 'Atlanta', 'River', '2023-11-10', 0),
(22, 'Boca', 'Chacarita', '2023-11-03', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id_persona` int(11) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `edad` int(11) DEFAULT NULL,
  `fk_id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id_persona`, `dni`, `nombre`, `apellido`, `edad`, `fk_id_usuario`) VALUES
(1, '42382349', 'Nombre1', 'Apellido1', NULL, NULL),
(2, '25560981', 'Nombre2', 'Apellido2', NULL, NULL),
(3, '19632400', 'Nombre3', 'Apellido3', NULL, NULL),
(4, '33254417', 'Nombre4', 'Apellido4', NULL, NULL),
(5, '28385463', 'Nombre5', 'Apellido5', NULL, NULL),
(6, '37334279', 'Nombre6', 'Apellido6', NULL, NULL),
(7, '32529631', 'Nombre7', 'Apellido7', NULL, NULL),
(8, '35995411', 'Nombre8', 'Apellido8', NULL, NULL),
(9, '37579419', 'Nombre9', 'Apellido9', NULL, NULL),
(10, '25469541', 'Nombre10', 'Apellido10', NULL, NULL),
(11, '31799881', 'Nombre11', 'Apellido11', NULL, NULL),
(12, '41034616', 'Nombre12', 'Apellido12', NULL, NULL),
(13, '40482394', 'Nombre13', 'Apellido13', NULL, NULL),
(14, '35720751', 'Nombre14', 'Apellido14', NULL, NULL),
(15, '26382240', 'Nombre15', 'Apellido15', NULL, NULL),
(16, '2344', 'ian', 'tepper', 35, 21),
(17, '43314', 'qwe', 'qwe', 34, 22),
(18, '22080167', 'juan', 'Lopez', 22, 23),
(19, '567756', 'julio', 'armegon', 18, 24),
(20, '567756', 'peter', 'alfonso', 20, 25),
(21, '21233421', 'homer', 'simpson', 44, 26),
(22, '45290042', 'Juan', 'Garasto', 21, 22);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resultado`
--

CREATE TABLE `resultado` (
  `id_resultado` int(11) NOT NULL,
  `ganador` varchar(255) DEFAULT NULL,
  `fk_id_partido` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `resultado`
--

INSERT INTO `resultado` (`id_resultado`, `ganador`, `fk_id_partido`) VALUES
(1, 'pendiente', 1),
(2, 'visitante', 2),
(3, 'local', 3),
(4, 'local', 4),
(5, 'local', 5),
(6, 'local', 6),
(7, 'local', 7),
(8, 'local', 8),
(9, 'local', 9),
(10, 'local', 10),
(11, 'local', 11),
(12, 'local', 12),
(13, 'local', 13),
(14, 'local', 14),
(15, 'local', 15),
(16, 'visitante', 16),
(17, NULL, 17),
(18, 'visitante', 18),
(19, NULL, 19),
(22, 'visitante', 22);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  `dinero` decimal(10,2) DEFAULT 0.00,
  `rol` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `usuario`, `contrasenia`, `dinero`, `rol`) VALUES
(1, 'Usuario1', 'Contraseña1', 16500.00, NULL),
(2, 'Usuario2', 'Contraseña2', 0.00, NULL),
(3, 'Usuario3', 'Contraseña3', 0.00, NULL),
(4, 'Usuario4', 'Contraseña4', 0.00, NULL),
(5, 'Usuario5', 'Contraseña5', 0.00, NULL),
(6, 'Usuario6', 'Contraseña6', 0.00, NULL),
(7, 'Usuario7', 'Contraseña7', 0.00, NULL),
(8, 'Usuario8', 'Contraseña8', 0.00, NULL),
(9, 'Usuario9', 'Contraseña9', 0.00, NULL),
(10, 'Usuario10', 'Contraseña10', 0.00, NULL),
(11, 'Usuario11', 'Contraseña11', 0.00, NULL),
(12, 'Usuario12', 'Contraseña12', 0.00, NULL),
(13, 'Usuario13', 'Contraseña13', 0.00, NULL),
(14, 'Usuario14', 'Contraseña14', 0.00, NULL),
(15, 'Usuario15', 'Contraseña15', 0.00, NULL),
(21, 'ianalan', 'contraseña', 0.00, NULL),
(22, '1', '1', 7272.00, 'NoRol'),
(23, 'juan', 'j', 0.00, NULL),
(24, 'user', 'r', 0.00, NULL),
(25, 'peter', 'i', 0.00, NULL),
(26, 'homer', 'h', 0.00, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `apuesta`
--
ALTER TABLE `apuesta`
  ADD PRIMARY KEY (`id_apuesta`),
  ADD KEY `fk_id_resultado` (`fk_id_resultado`),
  ADD KEY `fk_id_usuario` (`fk_id_usuario`),
  ADD KEY `fk_id_partido` (`fk_id_partido`);

--
-- Indices de la tabla `partido`
--
ALTER TABLE `partido`
  ADD PRIMARY KEY (`id_partido`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id_persona`),
  ADD KEY `fk_id_usuario` (`fk_id_usuario`);

--
-- Indices de la tabla `resultado`
--
ALTER TABLE `resultado`
  ADD PRIMARY KEY (`id_resultado`),
  ADD KEY `fk_id_partido` (`fk_id_partido`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `apuesta`
--
ALTER TABLE `apuesta`
  MODIFY `id_apuesta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `id_partido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `resultado`
--
ALTER TABLE `resultado`
  MODIFY `id_resultado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `apuesta`
--
ALTER TABLE `apuesta`
  ADD CONSTRAINT `apuesta_ibfk_3` FOREIGN KEY (`fk_id_resultado`) REFERENCES `resultado` (`id_resultado`),
  ADD CONSTRAINT `apuesta_ibfk_4` FOREIGN KEY (`fk_id_usuario`) REFERENCES `usuario` (`id_usuario`),
  ADD CONSTRAINT `apuesta_ibfk_5` FOREIGN KEY (`fk_id_partido`) REFERENCES `partido` (`id_partido`),
  ADD CONSTRAINT `fk_apuesta_resultado` FOREIGN KEY (`fk_id_resultado`) REFERENCES `resultado` (`id_resultado`);

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`fk_id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `resultado`
--
ALTER TABLE `resultado`
  ADD CONSTRAINT `resultado_ibfk_1` FOREIGN KEY (`fk_id_partido`) REFERENCES `partido` (`id_partido`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
