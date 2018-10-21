SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

USE [RAILSYSTEM3]
GO
ALTER PROCEDURE AddTrainRoutes
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


			-- Validate a train name and it's category to be valid
			IF dbo.uf_ValidateName(@TrainName) <> 1 OR dbo.uf_ValidateTrainCategory(@TrainCategory) <> 1
			BEGIN 
				RAISERROR('Name and Train cateogory should be valid', 14, 1)
			END
			INSERT INTO Train (name, category) VALUES (@TrainName, @TrainCategory)

			DECLARE @troute_id INT
			DECLARE @train_id  INT

			SET @troute_id = (SELECT troute_id FROM TRoute WHERE name=@RouteName)
			SET @train_id  = (SELECT train_id FROM Train WHERE name=@TrainName)
			INSERT INTO TrainRoute(train_id, troute_id) VALUES(@train_id, @troute_id)
	
			COMMIT TRAN
			SELECT 'Transaction comitted'
			END TRY

			BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Transaction rollbacked'
		END CATCH
END
GO 
				

SELECT * FROM TRoute
SELECT * FROM Train
SELECT * FROM TrainRoute
EXEC AddTrainRoutes 'EUROPA',NULL,'Eurocity'
SELECT * FROM TRoute
SELECT * FROM Train
SELECT * FROM TrainRoute