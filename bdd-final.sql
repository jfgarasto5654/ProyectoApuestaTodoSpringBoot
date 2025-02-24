-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-02-2025 a las 04:21:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partido`
--

CREATE TABLE `partido` (
  `id_partido` int(11) NOT NULL,
  `local` varchar(255) NOT NULL,
  `visitante` varchar(255) NOT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `balance` int(11) NOT NULL,
  `activo` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `partido`
--

INSERT INTO `partido` (`id_partido`, `local`, `visitante`, `fecha`, `balance`, `activo`) VALUES
(1, 'pendiente', 'pendiente', '00/00/0000', 0, 0);

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
(1, '1111111', 'admin', 'admin', 100, 1);

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
(1, 'pendiente', 1);

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
(1, '1', '1', 0.00, 'admin');

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
  MODIFY `id_apuesta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `id_partido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `resultado`
--
ALTER TABLE `resultado`
  MODIFY `id_resultado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
