-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE AddTrainRoutesStepByStep
	@RouteName		VARCHAR(50),
	@TrainName		VARCHAR(50),
	@TrainCategory	VARCHAR(50)
AS
BEGIN
	BEGIN TRAN
		BEGIN TRY
			-- Validate a route to be valid
			IF (dbo.uf_ValidateName(@RouteName) <> 1)
			BEGIN 
				RAISERROR('Name should not be null or be of length 0', 14, 1)
			END
			INSERT INTO TRoute (name) VALUES (@RouteName)
			COMMIT TRAN
			SELECT 'Transaction commited for route'
		END TRY
		BEGIN CATCH
		ROLLBACK TRAN 
			SELECT 'Transaction rollbacked for route'
		END CATCH

		BEGIN TRAN
			BEGIN TRY
				-- Validate a train name and it's category to be valid
				IF dbo.uf_ValidateName(@TrainName) <> 1 OR dbo.uf_ValidateTrainCategory(@TrainCategory) <> 1
				BEGIN 
					RAISERROR('Name and Train cateogory should be valid', 14, 1)
				END
				INSERT INTO Train (name, category) VALUES (@TrainName, @TrainCategory)
				COMMIT TRAN
				SELECT 'Transaction commited for a train'
			END TRY
			BEGIN CATCH
			ROLLBACK TRAN
				SELECT 'Transaction rollbacked for train'
			END CATCH

				
		BEGIN TRAN
			BEGIN TRY
				DECLARE @troute_id INT
				DECLARE @train_id  INT

				SET @troute_id = (SELECT troute_id FROM TRoute WHERE name=@RouteName)
				SET @train_id  = (SELECT train_id FROM Train WHERE name=@TrainName)
				INSERT INTO TrainRoute(train_id, troute_id) VALUES(@train_id, @troute_id)
	
				COMMIT TRAN
				SELECT 'Transaction comitted for TrainRoute'
			END TRY
			BEGIN CATCH
			ROLLBACK TRAN
				SELECT 'Transaction rollbacked for TrainRoute'
			END CATCH
END
GO 

DELETE FROM TrainRoute
DELETE FROM TRoute
DELETE FROM Train


SELECT * FROM TRoute
SELECT * FROM Train
SELECT * FROM TrainRoute
EXEC AddTrainRoutesStepByStep 'Bucuresti','Galati','Eurocity'
SELECT * FROM TRoute
SELECT * FROM Train
SELECT * FROM TrainRoute
