USE [RAILSYSTEM3]
GO

/*
Create a stored procedure that inserts data in tables that are in a many to many relation.
If any part of the operation fails, it must be all rolled back. (grade: 3)
*/

/* Validates that a name is not null and it's length is greater than 0 */
CREATE FUNCTION uf_ValidateName (@name varchar(100)) RETURNS BIT AS
BEGIN
DECLARE @return BIT
SET @return = 0
IF (@name IS NOT NULL) OR (LEN(@name) > 0)
SET @return = 1
RETURN @return
END


GO
/* Validate that a train's category is valid*/ 
CREATE FUNCTION uf_ValidateTrainCategory (@category varchar(100)) RETURNS BIT AS 
BEGIN
DECLARE @return BIT
SET @return = 0
IF (@category in ('Intercity', 'Express', 'Eurocity', 'Euronight', 'Railjet'))
SET @return = 1
RETURN @return 
END





