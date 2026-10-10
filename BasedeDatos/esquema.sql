--
-- PostgreSQL database dump
--

\restrict 9RaIGzCsMKT8OSUgXNmyLO23Y0upS155e2jw15HeUzsZgatWx42jOSxlVEsZ8ka

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

-- Started on 2026-10-09 21:08:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 230 (class 1259 OID 32835)
-- Name: articulo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.articulo (
    id_articulo integer NOT NULL,
    id_categoria integer NOT NULL,
    id_ubicacion integer NOT NULL,
    codigo_inventario character varying(50) NOT NULL,
    descripcion character varying(250) NOT NULL,
    marca character varying(100),
    modelo character varying(100),
    numero_serie character varying(100),
    condicion character varying(50),
    fecha_ingreso date NOT NULL,
    observaciones text,
    tipo_control character varying(20) NOT NULL,
    stock_minimo integer DEFAULT 0 NOT NULL,
    CONSTRAINT chk_stock_minimo CHECK ((stock_minimo >= 0)),
    CONSTRAINT chk_tipo_control CHECK (((tipo_control)::text = ANY ((ARRAY['INDIVIDUAL'::character varying, 'CANTIDAD'::character varying])::text[])))
);


ALTER TABLE public.articulo OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 32834)
-- Name: articulo_id_articulo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.articulo_id_articulo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.articulo_id_articulo_seq OWNER TO postgres;

--
-- TOC entry 5119 (class 0 OID 0)
-- Dependencies: 229
-- Name: articulo_id_articulo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.articulo_id_articulo_seq OWNED BY public.articulo.id_articulo;


--
-- TOC entry 220 (class 1259 OID 32769)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id_categoria integer NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 32768)
-- Name: categoria_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_id_categoria_seq OWNER TO postgres;

--
-- TOC entry 5120 (class 0 OID 0)
-- Dependencies: 219
-- Name: categoria_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;


--
-- TOC entry 251 (class 1259 OID 33100)
-- Name: detalle_devolucion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_devolucion (
    id_detalle_devolucion integer NOT NULL,
    id_devolucion integer NOT NULL,
    id_detalle_prestamo integer NOT NULL,
    cantidad integer NOT NULL,
    condicion character varying(50),
    observaciones text,
    CONSTRAINT chk_detalle_devolucion_cantidad CHECK ((cantidad > 0))
);


ALTER TABLE public.detalle_devolucion OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 33099)
-- Name: detalle_devolucion_id_detalle_devolucion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_devolucion_id_detalle_devolucion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_devolucion_id_detalle_devolucion_seq OWNER TO postgres;

--
-- TOC entry 5121 (class 0 OID 0)
-- Dependencies: 250
-- Name: detalle_devolucion_id_detalle_devolucion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_devolucion_id_detalle_devolucion_seq OWNED BY public.detalle_devolucion.id_detalle_devolucion;


--
-- TOC entry 239 (class 1259 OID 32944)
-- Name: detalle_donacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_donacion (
    id_detalle_donacion integer NOT NULL,
    id_donacion integer NOT NULL,
    id_articulo integer NOT NULL,
    cantidad integer NOT NULL,
    condicion character varying(50),
    observaciones text,
    CONSTRAINT chk_detalle_donacion_cantidad CHECK ((cantidad > 0))
);


ALTER TABLE public.detalle_donacion OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 32943)
-- Name: detalle_donacion_id_detalle_donacion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_donacion_id_detalle_donacion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_donacion_id_detalle_donacion_seq OWNER TO postgres;

--
-- TOC entry 5122 (class 0 OID 0)
-- Dependencies: 238
-- Name: detalle_donacion_id_detalle_donacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_donacion_id_detalle_donacion_seq OWNED BY public.detalle_donacion.id_detalle_donacion;


--
-- TOC entry 243 (class 1259 OID 32991)
-- Name: detalle_entrega; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_entrega (
    id_detalle_entrega integer NOT NULL,
    id_entrega integer NOT NULL,
    id_articulo integer NOT NULL,
    cantidad integer NOT NULL,
    observaciones text,
    CONSTRAINT chk_detalle_entrega_cantidad CHECK ((cantidad > 0))
);


ALTER TABLE public.detalle_entrega OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 32990)
-- Name: detalle_entrega_id_detalle_entrega_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_entrega_id_detalle_entrega_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_entrega_id_detalle_entrega_seq OWNER TO postgres;

--
-- TOC entry 5123 (class 0 OID 0)
-- Dependencies: 242
-- Name: detalle_entrega_id_detalle_entrega_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_entrega_id_detalle_entrega_seq OWNED BY public.detalle_entrega.id_detalle_entrega;


--
-- TOC entry 247 (class 1259 OID 33047)
-- Name: detalle_prestamo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_prestamo (
    id_detalle_prestamo integer NOT NULL,
    id_prestamo integer NOT NULL,
    id_articulo integer NOT NULL,
    cantidad integer NOT NULL,
    observaciones text,
    CONSTRAINT chk_detalle_prestamo_cantidad CHECK ((cantidad > 0))
);


ALTER TABLE public.detalle_prestamo OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 33046)
-- Name: detalle_prestamo_id_detalle_prestamo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_prestamo_id_detalle_prestamo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_prestamo_id_detalle_prestamo_seq OWNER TO postgres;

--
-- TOC entry 5124 (class 0 OID 0)
-- Dependencies: 246
-- Name: detalle_prestamo_id_detalle_prestamo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_prestamo_id_detalle_prestamo_seq OWNED BY public.detalle_prestamo.id_detalle_prestamo;


--
-- TOC entry 255 (class 1259 OID 33150)
-- Name: detalle_reserva; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.detalle_reserva (
    id_detalle_reserva integer NOT NULL,
    id_reserva integer NOT NULL,
    id_articulo integer NOT NULL,
    cantidad integer NOT NULL,
    observaciones text,
    CONSTRAINT chk_detalle_reserva_cantidad CHECK ((cantidad > 0))
);


ALTER TABLE public.detalle_reserva OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 33149)
-- Name: detalle_reserva_id_detalle_reserva_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.detalle_reserva_id_detalle_reserva_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detalle_reserva_id_detalle_reserva_seq OWNER TO postgres;

--
-- TOC entry 5125 (class 0 OID 0)
-- Dependencies: 254
-- Name: detalle_reserva_id_detalle_reserva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.detalle_reserva_id_detalle_reserva_seq OWNED BY public.detalle_reserva.id_detalle_reserva;


--
-- TOC entry 249 (class 1259 OID 33071)
-- Name: devolucion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.devolucion (
    id_devolucion integer NOT NULL,
    id_prestamo integer NOT NULL,
    id_tercero integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_devolucion date NOT NULL,
    observaciones text
);


ALTER TABLE public.devolucion OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 33070)
-- Name: devolucion_id_devolucion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.devolucion_id_devolucion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.devolucion_id_devolucion_seq OWNER TO postgres;

--
-- TOC entry 5126 (class 0 OID 0)
-- Dependencies: 248
-- Name: devolucion_id_devolucion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.devolucion_id_devolucion_seq OWNED BY public.devolucion.id_devolucion;


--
-- TOC entry 237 (class 1259 OID 32921)
-- Name: donacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.donacion (
    id_donacion integer NOT NULL,
    id_tercero integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_donacion date NOT NULL,
    tipo_donacion character varying(100),
    observaciones text
);


ALTER TABLE public.donacion OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 32920)
-- Name: donacion_id_donacion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.donacion_id_donacion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.donacion_id_donacion_seq OWNER TO postgres;

--
-- TOC entry 5127 (class 0 OID 0)
-- Dependencies: 236
-- Name: donacion_id_donacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.donacion_id_donacion_seq OWNED BY public.donacion.id_donacion;


--
-- TOC entry 241 (class 1259 OID 32968)
-- Name: entrega; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entrega (
    id_entrega integer NOT NULL,
    id_tercero integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_entrega date NOT NULL,
    motivo character varying(250),
    campana character varying(150),
    observaciones text
);


ALTER TABLE public.entrega OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 32967)
-- Name: entrega_id_entrega_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.entrega_id_entrega_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.entrega_id_entrega_seq OWNER TO postgres;

--
-- TOC entry 5128 (class 0 OID 0)
-- Dependencies: 240
-- Name: entrega_id_entrega_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.entrega_id_entrega_seq OWNED BY public.entrega.id_entrega;


--
-- TOC entry 234 (class 1259 OID 32885)
-- Name: fotografia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fotografia (
    id_fotografia integer NOT NULL,
    id_articulo integer NOT NULL,
    ruta_archivo text NOT NULL,
    descripcion text,
    fecha_carga timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.fotografia OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 32884)
-- Name: fotografia_id_fotografia_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fotografia_id_fotografia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fotografia_id_fotografia_seq OWNER TO postgres;

--
-- TOC entry 5129 (class 0 OID 0)
-- Dependencies: 233
-- Name: fotografia_id_fotografia_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fotografia_id_fotografia_seq OWNED BY public.fotografia.id_fotografia;


--
-- TOC entry 257 (class 1259 OID 33174)
-- Name: movimiento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movimiento (
    id_movimiento integer NOT NULL,
    id_articulo integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_movimiento timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    tipo_movimiento character varying(40) NOT NULL,
    cantidad integer NOT NULL,
    estado_anterior character varying(30),
    estado_nuevo character varying(30),
    observaciones text,
    CONSTRAINT chk_movimiento_cantidad CHECK ((cantidad > 0)),
    CONSTRAINT chk_movimiento_tipo CHECK (((tipo_movimiento)::text = ANY ((ARRAY['INGRESO_DONACION'::character varying, 'RESERVA'::character varying, 'SALIDA_PRESTAMO'::character varying, 'DEVOLUCION'::character varying, 'ENTREGA_DEFINITIVA'::character varying, 'ENTRADA_REPARACION'::character varying, 'SALIDA_REPARACION'::character varying, 'AJUSTE_STOCK'::character varying])::text[])))
);


ALTER TABLE public.movimiento OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 33173)
-- Name: movimiento_id_movimiento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movimiento_id_movimiento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.movimiento_id_movimiento_seq OWNER TO postgres;

--
-- TOC entry 5130 (class 0 OID 0)
-- Dependencies: 256
-- Name: movimiento_id_movimiento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movimiento_id_movimiento_seq OWNED BY public.movimiento.id_movimiento;


--
-- TOC entry 245 (class 1259 OID 33015)
-- Name: prestamo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prestamo (
    id_prestamo integer NOT NULL,
    id_tercero integer NOT NULL,
    id_usuario_registra integer NOT NULL,
    id_usuario_autoriza integer NOT NULL,
    fecha_prestamo date NOT NULL,
    fecha_devolucion_prevista date,
    estado character varying(30) DEFAULT 'ACTIVO'::character varying NOT NULL,
    observaciones text,
    CONSTRAINT chk_prestamo_estado CHECK (((estado)::text = ANY ((ARRAY['ACTIVO'::character varying, 'DEVUELTO'::character varying, 'VENCIDO'::character varying, 'ANULADO'::character varying])::text[])))
);


ALTER TABLE public.prestamo OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 33014)
-- Name: prestamo_id_prestamo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prestamo_id_prestamo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.prestamo_id_prestamo_seq OWNER TO postgres;

--
-- TOC entry 5131 (class 0 OID 0)
-- Dependencies: 244
-- Name: prestamo_id_prestamo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prestamo_id_prestamo_seq OWNED BY public.prestamo.id_prestamo;


--
-- TOC entry 253 (class 1259 OID 33124)
-- Name: reserva; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reserva (
    id_reserva integer NOT NULL,
    id_tercero integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_reserva date NOT NULL,
    fecha_vencimiento date,
    estado character varying(30) DEFAULT 'PENDIENTE'::character varying NOT NULL,
    observaciones text,
    CONSTRAINT chk_reserva_estado CHECK (((estado)::text = ANY ((ARRAY['PENDIENTE'::character varying, 'CONFIRMADA'::character varying, 'CANCELADA'::character varying, 'COMPLETADA'::character varying])::text[])))
);


ALTER TABLE public.reserva OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 33123)
-- Name: reserva_id_reserva_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reserva_id_reserva_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reserva_id_reserva_seq OWNER TO postgres;

--
-- TOC entry 5132 (class 0 OID 0)
-- Dependencies: 252
-- Name: reserva_id_reserva_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reserva_id_reserva_seq OWNED BY public.reserva.id_reserva;


--
-- TOC entry 228 (class 1259 OID 32822)
-- Name: rol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text
);


ALTER TABLE public.rol OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 32821)
-- Name: rol_id_rol_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.rol_id_rol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rol_id_rol_seq OWNER TO postgres;

--
-- TOC entry 5133 (class 0 OID 0)
-- Dependencies: 227
-- Name: rol_id_rol_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.rol_id_rol_seq OWNED BY public.rol.id_rol;


--
-- TOC entry 232 (class 1259 OID 32864)
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    id_stock integer NOT NULL,
    id_articulo integer NOT NULL,
    cantidad integer DEFAULT 0 NOT NULL,
    estado character varying(30) NOT NULL,
    CONSTRAINT chk_stock_cantidad CHECK ((cantidad >= 0)),
    CONSTRAINT chk_stock_estado CHECK (((estado)::text = ANY ((ARRAY['DISPONIBLE'::character varying, 'RESERVADO'::character varying, 'PRESTADO'::character varying, 'ENTREGADO'::character varying, 'REPARACION'::character varying, 'BAJA'::character varying])::text[])))
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 32863)
-- Name: stock_id_stock_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_id_stock_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stock_id_stock_seq OWNER TO postgres;

--
-- TOC entry 5134 (class 0 OID 0)
-- Dependencies: 231
-- Name: stock_id_stock_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stock_id_stock_seq OWNED BY public.stock.id_stock;


--
-- TOC entry 224 (class 1259 OID 32791)
-- Name: tercero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tercero (
    id_tercero integer NOT NULL,
    nombre character varying(150) NOT NULL,
    tipo_tercero character varying(30) NOT NULL,
    documento character varying(50),
    telefono character varying(50),
    direccion character varying(250),
    observaciones text,
    CONSTRAINT chk_tipo_tercero CHECK (((tipo_tercero)::text = ANY ((ARRAY['PERSONA'::character varying, 'FAMILIA'::character varying, 'INSTITUCION'::character varying, 'ORGANIZACION'::character varying])::text[])))
);


ALTER TABLE public.tercero OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 32790)
-- Name: tercero_id_tercero_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tercero_id_tercero_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tercero_id_tercero_seq OWNER TO postgres;

--
-- TOC entry 5135 (class 0 OID 0)
-- Dependencies: 223
-- Name: tercero_id_tercero_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tercero_id_tercero_seq OWNED BY public.tercero.id_tercero;


--
-- TOC entry 222 (class 1259 OID 32780)
-- Name: ubicacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ubicacion (
    id_ubicacion integer NOT NULL,
    nombre character varying(100) NOT NULL,
    descripcion text
);


ALTER TABLE public.ubicacion OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 32779)
-- Name: ubicacion_id_ubicacion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ubicacion_id_ubicacion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ubicacion_id_ubicacion_seq OWNER TO postgres;

--
-- TOC entry 5136 (class 0 OID 0)
-- Dependencies: 221
-- Name: ubicacion_id_ubicacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ubicacion_id_ubicacion_seq OWNED BY public.ubicacion.id_ubicacion;


--
-- TOC entry 226 (class 1259 OID 32804)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nombre character varying(100) NOT NULL,
    apellido character varying(100) NOT NULL,
    usuario character varying(50) NOT NULL,
    contrasena text NOT NULL,
    email character varying(150),
    activo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 32803)
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_usuario_seq OWNER TO postgres;

--
-- TOC entry 5137 (class 0 OID 0)
-- Dependencies: 225
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- TOC entry 235 (class 1259 OID 32903)
-- Name: usuario_rol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_rol (
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL
);


ALTER TABLE public.usuario_rol OWNER TO postgres;

--
-- TOC entry 4855 (class 2604 OID 32838)
-- Name: articulo id_articulo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.articulo ALTER COLUMN id_articulo SET DEFAULT nextval('public.articulo_id_articulo_seq'::regclass);


--
-- TOC entry 4849 (class 2604 OID 32772)
-- Name: categoria id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_id_categoria_seq'::regclass);


--
-- TOC entry 4869 (class 2604 OID 33103)
-- Name: detalle_devolucion id_detalle_devolucion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_devolucion ALTER COLUMN id_detalle_devolucion SET DEFAULT nextval('public.detalle_devolucion_id_detalle_devolucion_seq'::regclass);


--
-- TOC entry 4862 (class 2604 OID 32947)
-- Name: detalle_donacion id_detalle_donacion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_donacion ALTER COLUMN id_detalle_donacion SET DEFAULT nextval('public.detalle_donacion_id_detalle_donacion_seq'::regclass);


--
-- TOC entry 4864 (class 2604 OID 32994)
-- Name: detalle_entrega id_detalle_entrega; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_entrega ALTER COLUMN id_detalle_entrega SET DEFAULT nextval('public.detalle_entrega_id_detalle_entrega_seq'::regclass);


--
-- TOC entry 4867 (class 2604 OID 33050)
-- Name: detalle_prestamo id_detalle_prestamo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_prestamo ALTER COLUMN id_detalle_prestamo SET DEFAULT nextval('public.detalle_prestamo_id_detalle_prestamo_seq'::regclass);


--
-- TOC entry 4872 (class 2604 OID 33153)
-- Name: detalle_reserva id_detalle_reserva; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva ALTER COLUMN id_detalle_reserva SET DEFAULT nextval('public.detalle_reserva_id_detalle_reserva_seq'::regclass);


--
-- TOC entry 4868 (class 2604 OID 33074)
-- Name: devolucion id_devolucion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devolucion ALTER COLUMN id_devolucion SET DEFAULT nextval('public.devolucion_id_devolucion_seq'::regclass);


--
-- TOC entry 4861 (class 2604 OID 32924)
-- Name: donacion id_donacion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donacion ALTER COLUMN id_donacion SET DEFAULT nextval('public.donacion_id_donacion_seq'::regclass);


--
-- TOC entry 4863 (class 2604 OID 32971)
-- Name: entrega id_entrega; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrega ALTER COLUMN id_entrega SET DEFAULT nextval('public.entrega_id_entrega_seq'::regclass);


--
-- TOC entry 4859 (class 2604 OID 32888)
-- Name: fotografia id_fotografia; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fotografia ALTER COLUMN id_fotografia SET DEFAULT nextval('public.fotografia_id_fotografia_seq'::regclass);


--
-- TOC entry 4873 (class 2604 OID 33177)
-- Name: movimiento id_movimiento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento ALTER COLUMN id_movimiento SET DEFAULT nextval('public.movimiento_id_movimiento_seq'::regclass);


--
-- TOC entry 4865 (class 2604 OID 33018)
-- Name: prestamo id_prestamo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prestamo ALTER COLUMN id_prestamo SET DEFAULT nextval('public.prestamo_id_prestamo_seq'::regclass);


--
-- TOC entry 4870 (class 2604 OID 33127)
-- Name: reserva id_reserva; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserva ALTER COLUMN id_reserva SET DEFAULT nextval('public.reserva_id_reserva_seq'::regclass);


--
-- TOC entry 4854 (class 2604 OID 32825)
-- Name: rol id_rol; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol ALTER COLUMN id_rol SET DEFAULT nextval('public.rol_id_rol_seq'::regclass);


--
-- TOC entry 4857 (class 2604 OID 32867)
-- Name: stock id_stock; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock ALTER COLUMN id_stock SET DEFAULT nextval('public.stock_id_stock_seq'::regclass);


--
-- TOC entry 4851 (class 2604 OID 32794)
-- Name: tercero id_tercero; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tercero ALTER COLUMN id_tercero SET DEFAULT nextval('public.tercero_id_tercero_seq'::regclass);


--
-- TOC entry 4850 (class 2604 OID 32783)
-- Name: ubicacion id_ubicacion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ubicacion ALTER COLUMN id_ubicacion SET DEFAULT nextval('public.ubicacion_id_ubicacion_seq'::regclass);


--
-- TOC entry 4852 (class 2604 OID 32807)
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- TOC entry 4904 (class 2606 OID 32852)
-- Name: articulo articulo_codigo_inventario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.articulo
    ADD CONSTRAINT articulo_codigo_inventario_key UNIQUE (codigo_inventario);


--
-- TOC entry 4906 (class 2606 OID 32850)
-- Name: articulo articulo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.articulo
    ADD CONSTRAINT articulo_pkey PRIMARY KEY (id_articulo);


--
-- TOC entry 4890 (class 2606 OID 32778)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria);


--
-- TOC entry 4930 (class 2606 OID 33112)
-- Name: detalle_devolucion detalle_devolucion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_devolucion
    ADD CONSTRAINT detalle_devolucion_pkey PRIMARY KEY (id_detalle_devolucion);


--
-- TOC entry 4918 (class 2606 OID 32956)
-- Name: detalle_donacion detalle_donacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_donacion
    ADD CONSTRAINT detalle_donacion_pkey PRIMARY KEY (id_detalle_donacion);


--
-- TOC entry 4922 (class 2606 OID 33003)
-- Name: detalle_entrega detalle_entrega_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_entrega
    ADD CONSTRAINT detalle_entrega_pkey PRIMARY KEY (id_detalle_entrega);


--
-- TOC entry 4926 (class 2606 OID 33059)
-- Name: detalle_prestamo detalle_prestamo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_prestamo
    ADD CONSTRAINT detalle_prestamo_pkey PRIMARY KEY (id_detalle_prestamo);


--
-- TOC entry 4934 (class 2606 OID 33162)
-- Name: detalle_reserva detalle_reserva_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva
    ADD CONSTRAINT detalle_reserva_pkey PRIMARY KEY (id_detalle_reserva);


--
-- TOC entry 4928 (class 2606 OID 33083)
-- Name: devolucion devolucion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devolucion
    ADD CONSTRAINT devolucion_pkey PRIMARY KEY (id_devolucion);


--
-- TOC entry 4916 (class 2606 OID 32932)
-- Name: donacion donacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donacion
    ADD CONSTRAINT donacion_pkey PRIMARY KEY (id_donacion);


--
-- TOC entry 4920 (class 2606 OID 32979)
-- Name: entrega entrega_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrega
    ADD CONSTRAINT entrega_pkey PRIMARY KEY (id_entrega);


--
-- TOC entry 4912 (class 2606 OID 32897)
-- Name: fotografia fotografia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fotografia
    ADD CONSTRAINT fotografia_pkey PRIMARY KEY (id_fotografia);


--
-- TOC entry 4936 (class 2606 OID 33190)
-- Name: movimiento movimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT movimiento_pkey PRIMARY KEY (id_movimiento);


--
-- TOC entry 4924 (class 2606 OID 33030)
-- Name: prestamo prestamo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prestamo
    ADD CONSTRAINT prestamo_pkey PRIMARY KEY (id_prestamo);


--
-- TOC entry 4932 (class 2606 OID 33138)
-- Name: reserva reserva_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserva
    ADD CONSTRAINT reserva_pkey PRIMARY KEY (id_reserva);


--
-- TOC entry 4900 (class 2606 OID 32833)
-- Name: rol rol_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_nombre_key UNIQUE (nombre);


--
-- TOC entry 4902 (class 2606 OID 32831)
-- Name: rol rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id_rol);


--
-- TOC entry 4908 (class 2606 OID 32876)
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id_stock);


--
-- TOC entry 4894 (class 2606 OID 32802)
-- Name: tercero tercero_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tercero
    ADD CONSTRAINT tercero_pkey PRIMARY KEY (id_tercero);


--
-- TOC entry 4892 (class 2606 OID 32789)
-- Name: ubicacion ubicacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ubicacion
    ADD CONSTRAINT ubicacion_pkey PRIMARY KEY (id_ubicacion);


--
-- TOC entry 4910 (class 2606 OID 32878)
-- Name: stock uq_stock_articulo_estado; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT uq_stock_articulo_estado UNIQUE (id_articulo, estado);


--
-- TOC entry 4896 (class 2606 OID 32818)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);


--
-- TOC entry 4914 (class 2606 OID 32909)
-- Name: usuario_rol usuario_rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_pkey PRIMARY KEY (id_usuario, id_rol);


--
-- TOC entry 4898 (class 2606 OID 32820)
-- Name: usuario usuario_usuario_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_usuario_key UNIQUE (usuario);


--
-- TOC entry 4937 (class 2606 OID 32853)
-- Name: articulo fk_articulo_categoria; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.articulo
    ADD CONSTRAINT fk_articulo_categoria FOREIGN KEY (id_categoria) REFERENCES public.categoria(id_categoria);


--
-- TOC entry 4938 (class 2606 OID 32858)
-- Name: articulo fk_articulo_ubicacion; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.articulo
    ADD CONSTRAINT fk_articulo_ubicacion FOREIGN KEY (id_ubicacion) REFERENCES public.ubicacion(id_ubicacion);


--
-- TOC entry 4959 (class 2606 OID 33113)
-- Name: detalle_devolucion fk_detalle_devolucion_devolucion; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_devolucion
    ADD CONSTRAINT fk_detalle_devolucion_devolucion FOREIGN KEY (id_devolucion) REFERENCES public.devolucion(id_devolucion);


--
-- TOC entry 4960 (class 2606 OID 33118)
-- Name: detalle_devolucion fk_detalle_devolucion_prestamo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_devolucion
    ADD CONSTRAINT fk_detalle_devolucion_prestamo FOREIGN KEY (id_detalle_prestamo) REFERENCES public.detalle_prestamo(id_detalle_prestamo);


--
-- TOC entry 4945 (class 2606 OID 32962)
-- Name: detalle_donacion fk_detalle_donacion_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_donacion
    ADD CONSTRAINT fk_detalle_donacion_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4946 (class 2606 OID 32957)
-- Name: detalle_donacion fk_detalle_donacion_donacion; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_donacion
    ADD CONSTRAINT fk_detalle_donacion_donacion FOREIGN KEY (id_donacion) REFERENCES public.donacion(id_donacion);


--
-- TOC entry 4949 (class 2606 OID 33009)
-- Name: detalle_entrega fk_detalle_entrega_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_entrega
    ADD CONSTRAINT fk_detalle_entrega_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4950 (class 2606 OID 33004)
-- Name: detalle_entrega fk_detalle_entrega_entrega; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_entrega
    ADD CONSTRAINT fk_detalle_entrega_entrega FOREIGN KEY (id_entrega) REFERENCES public.entrega(id_entrega);


--
-- TOC entry 4954 (class 2606 OID 33065)
-- Name: detalle_prestamo fk_detalle_prestamo_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_prestamo
    ADD CONSTRAINT fk_detalle_prestamo_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4955 (class 2606 OID 33060)
-- Name: detalle_prestamo fk_detalle_prestamo_prestamo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_prestamo
    ADD CONSTRAINT fk_detalle_prestamo_prestamo FOREIGN KEY (id_prestamo) REFERENCES public.prestamo(id_prestamo);


--
-- TOC entry 4963 (class 2606 OID 33168)
-- Name: detalle_reserva fk_detalle_reserva_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva
    ADD CONSTRAINT fk_detalle_reserva_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4964 (class 2606 OID 33163)
-- Name: detalle_reserva fk_detalle_reserva_reserva; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.detalle_reserva
    ADD CONSTRAINT fk_detalle_reserva_reserva FOREIGN KEY (id_reserva) REFERENCES public.reserva(id_reserva);


--
-- TOC entry 4956 (class 2606 OID 33084)
-- Name: devolucion fk_devolucion_prestamo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devolucion
    ADD CONSTRAINT fk_devolucion_prestamo FOREIGN KEY (id_prestamo) REFERENCES public.prestamo(id_prestamo);


--
-- TOC entry 4957 (class 2606 OID 33089)
-- Name: devolucion fk_devolucion_tercero; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devolucion
    ADD CONSTRAINT fk_devolucion_tercero FOREIGN KEY (id_tercero) REFERENCES public.tercero(id_tercero);


--
-- TOC entry 4958 (class 2606 OID 33094)
-- Name: devolucion fk_devolucion_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devolucion
    ADD CONSTRAINT fk_devolucion_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4943 (class 2606 OID 32933)
-- Name: donacion fk_donacion_tercero; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donacion
    ADD CONSTRAINT fk_donacion_tercero FOREIGN KEY (id_tercero) REFERENCES public.tercero(id_tercero);


--
-- TOC entry 4944 (class 2606 OID 32938)
-- Name: donacion fk_donacion_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donacion
    ADD CONSTRAINT fk_donacion_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4947 (class 2606 OID 32980)
-- Name: entrega fk_entrega_tercero; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrega
    ADD CONSTRAINT fk_entrega_tercero FOREIGN KEY (id_tercero) REFERENCES public.tercero(id_tercero);


--
-- TOC entry 4948 (class 2606 OID 32985)
-- Name: entrega fk_entrega_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrega
    ADD CONSTRAINT fk_entrega_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4940 (class 2606 OID 32898)
-- Name: fotografia fk_fotografia_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fotografia
    ADD CONSTRAINT fk_fotografia_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4965 (class 2606 OID 33191)
-- Name: movimiento fk_movimiento_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk_movimiento_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4966 (class 2606 OID 33196)
-- Name: movimiento fk_movimiento_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk_movimiento_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4951 (class 2606 OID 33031)
-- Name: prestamo fk_prestamo_tercero; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prestamo
    ADD CONSTRAINT fk_prestamo_tercero FOREIGN KEY (id_tercero) REFERENCES public.tercero(id_tercero);


--
-- TOC entry 4952 (class 2606 OID 33041)
-- Name: prestamo fk_prestamo_usuario_autoriza; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prestamo
    ADD CONSTRAINT fk_prestamo_usuario_autoriza FOREIGN KEY (id_usuario_autoriza) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4953 (class 2606 OID 33036)
-- Name: prestamo fk_prestamo_usuario_registra; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prestamo
    ADD CONSTRAINT fk_prestamo_usuario_registra FOREIGN KEY (id_usuario_registra) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4961 (class 2606 OID 33139)
-- Name: reserva fk_reserva_tercero; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserva
    ADD CONSTRAINT fk_reserva_tercero FOREIGN KEY (id_tercero) REFERENCES public.tercero(id_tercero);


--
-- TOC entry 4962 (class 2606 OID 33144)
-- Name: reserva fk_reserva_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserva
    ADD CONSTRAINT fk_reserva_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- TOC entry 4939 (class 2606 OID 32879)
-- Name: stock fk_stock_articulo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT fk_stock_articulo FOREIGN KEY (id_articulo) REFERENCES public.articulo(id_articulo);


--
-- TOC entry 4941 (class 2606 OID 32915)
-- Name: usuario_rol fk_usuario_rol_rol; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT fk_usuario_rol_rol FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol);


--
-- TOC entry 4942 (class 2606 OID 32910)
-- Name: usuario_rol fk_usuario_rol_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT fk_usuario_rol_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


-- Completed on 2026-10-09 21:08:36

--
-- PostgreSQL database dump complete
--

\unrestrict 9RaIGzCsMKT8OSUgXNmyLO23Y0upS155e2jw15HeUzsZgatWx42jOSxlVEsZ8ka

