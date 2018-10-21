USE [RAILSYSTEM3]
GO
--------------------------
--		DIRTY READ      --
--------------------------

BEGIN TRANSACTION 
UPDATE Client SET name='Michael' WHERE client_id = 1001
WAITFOR DELAY '00:00:10'
ROLLBACK TRANSACTION

------------------------------------
--		NON-REPEATABLE READS      --
------------------------------------

INSERT INTO Client(name, age) VALUES('Gigel', 30)
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Client SET age=31 WHERE name='Gigel'
COMMIT TRAN

-----------------------------
--		Phantom Reads      --
-----------------------------
DELETE FROM Client WHERE name='Stefan'
BEGIN TRAN
WAITFOR DELAY '00:00:04'
INSERT INTO Client(name, age) VALUES ('Stefan', 24)
COMMIT TRAN


-----------------------------
--		  Deadlock		   --
-----------------------------

USE [RAILSYSTEM3]
GO
SELECT * FROM Client
SELECT * FROM Seat
UPDATE Client SET name='Random stuff' WHERE client_id=1018
UPDATE Seat SET seat_type='Something random' WHERE seat_id=5
-- transaction 1
BEGIN TRAN 
	UPDATE Client SET name='New Client Transaction 1' WHERE client_id=1018
	-- this transaction has exclusively lock on table Client
	WAITFOR DELAY '00:00:10'

	UPDATE Seat SET seat_type='Seat Type Transaction 1' WHERE seat_id=5
	-- this transaction will be blocked because transaction 2 has already blocked our lock on table Seat
	-- so, transaction 1 is blocked on an exclusevely block on table Seat
COMMIT TRAN


-- transaction 1
BEGIN TRAN 
	UPDATE Client SET name='New Client Transaction 1' WHERE client_id=1018
	-- this transaction has exclusively lock on table Client
	WAITFOR DELAY '00:00:10'

	UPDATE Seat SET seat_type='Seat Type Transaction 1' WHERE seat_id=5
COMMIT TRAN
-- this transaction is chose as a deadlock, because it has the lowest priority level here(normal)


SELECT * FROM Seat
SELECT * FROM Client
