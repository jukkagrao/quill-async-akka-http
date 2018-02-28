CREATE TABLE IF NOT EXISTS public.supplier
(
    id uuid,
    name character varying(64) NOT NULL,
    description text,
    PRIMARY KEY (id)
)