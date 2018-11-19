--Scripts

--Creacion
if object_id('melodias', 'U') is null 
CREATE TABLE melodias (
	nombreMelodia nvarchar(50) NOT NULL,
    instrumento nvarchar(50) NOT NULL,
    tempo nvarchar(50) NOT NULL,
    PRIMARY KEY (nombreMelodia)
);
if object_id('notas', 'U') is null 
CREATE TABLE notas (
	Id int NOT NULL,
	nombreMelodia nvarchar(50) NOT NULL,
	nombreNota nvarchar(50) NOT NULL,
    octava nvarchar(50) NOT NULL,
	figura nvarchar(50) NOT NULL,
	alteracion nvarchar(50) NOT NULL,
	PRIMARY KEY (Id, nombreMelodia)
);

--INSERT
INSERT notas (id, nombreMelodia, nombreNota,octava, figura,alteracion)
	VALUES ('2','loco','C','5','q','n')
INSERT melodias(nombreMelodia,instrumento,tempo)
VALUES('loco','PIANO','100')

--DELETE
DELETE FROM melodias 	
where melodias.nombreMelodia = 'flaca'

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
SELECT m.nombreMelodia,m.instrumento,m.tempo,n.Id,n.nombreNota,n.octava,n.figura,n.alteracion
	FROM melodias m INNER JOIN notas n 
	ON m.nombreMelodia = n.nombreMelodia
