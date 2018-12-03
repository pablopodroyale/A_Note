--Scripts
--Creacion
--Creacion
if object_id('canciones', 'U') is null 
CREATE TABLE canciones (
	IdCancion int IDENTITY(1,1) NOT NULL,
	nombreCancion nvarchar(250) UNIQUE NOT NULL,
	tempo nvarchar(50) NOT NULL,
    PRIMARY KEY (IdCancion)
);
if object_id('pistas', 'U') is null 
CREATE TABLE pistas (
	PistaID int IDENTITY(1,1) NOT NULL,
	IdCancion int NOT NULL,
	nombrePista nvarchar(250) UNIQUE NOT NULL,
	instrumento nvarchar(50) NOT NULL,
	PRIMARY KEY (PistaID),
	FOREIGN KEY(IdCancion) REFERENCES canciones(IdCancion)
);
if object_id('notas', 'U') is null 
CREATE TABLE notas (
	IdNota int IDENTITY(1,1) NOT NULL,
	PistaID int NOT NULL,
	nombreNota nvarchar(50) NOT NULL,
    octava nvarchar(50) NOT NULL,
	figura nvarchar(50) NOT NULL,
	alteracion nvarchar(50) NOT NULL,
	PRIMARY KEY (IdNota),
	FOREIGN KEY(PistaID) REFERENCES pistas(PistaID)
);
--INSERT
INSERT canciones (nombreCancion,tempo)
VALUES ('flaca','100')
INSERT pistas(IdCancion,nombrePista,instrumento)
VALUES(1,'verso','PIANO')
INSERT notas(PistaID,nombreNota,octava,figura,alteracion)
VALUES(1,'C','5','q','n')

--DELETE
DELETE notas 
WHERE nombreCancion = '%s'
DELETE pistas
WHERE nombreCancion = '%s'
DELETE canciones
WHERE nombreCancion = '%s'

--Update
UPDATE [dbo].[melodias]
   SET [nombreMelodia] = '%s'
      ,[instrumento] = '%s'
      ,[tempo] = '%s'
 WHERE nombreMelodia = '%s'
 
 --Disable pk
 ALTER TABLE notas NOCHECK CONSTRAINT FK_notas_melodias
 --Enable pk
 ALTER TABLE notas CHECK CONSTRAINT FK_notas_melodias
 
--Details
SELECT c.IdCancion,c.nombreCancion,c.tempo, p.PistaID, p.nombrePista,n.nombreNota,n.octava,n.figura,n.alteracion
FROM canciones c INNER JOIN pistas p 
ON c.IdCancion = p.IdCancion 
INNER JOIN notas n 
ON p.PistaID = n.PistaID
WHERE c.nombreCancion = 'flaca' AND p.nombrePista = 'verso'