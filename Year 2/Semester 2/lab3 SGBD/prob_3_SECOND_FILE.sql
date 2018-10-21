USE [RAILSYSTEM3]
GO
--------------------------
--		DIRTY READ      --
--------------------------


/*
READ UNCOMMITTED
Specifies that statements can read rows that have been modified by other
transactions but not yet committed. 
*/

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRAN
SELECT * FROM Client
WAITFOR DELAY '00:00:15'
SELECT * FROM Client
COMMIT TRAN


-- [SOLUTION]
/*
READ COMMITTED
Specifies that statements cannot read data that has been modified but not
committed by other transactions.
*/
SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
SELECT * FROM Client
WAITFOR DELAY '00:00:15'
SELECT * FROM Client
COMMIT TRAN


------------------------------------
--		NON-REPEATABLE READS      --
------------------------------------
SET TRANSACTION ISOLATION LEVEL READ COMMITED
BEGIN TRAN
SELECT * FROM Client
WAITFOR DELAY '00:00:05'
SELECT * FROM Client
COMMIT TRAN

-- [SOLUTION]
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
BEGIN TRAN
SELECT * FROM Client
WAITFOR DELAY '00:00:05'
SELECT * FROM Client
COMMIT TRAN

-----------------------------
--		Phantom Reads      --
-----------------------------

--Phantom Reads Part 1 (VALUE WILL BE INSERTED)
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
BEGIN TRAN
SELECT * FROM Client
WAITFOR DELAY '00:00:05'
SELECT * FROM Client
COMMIT TRAN

-- [SOLUTION]
/*
Statements cannot read data that has been modified but not yet committed
by other transactions.

No other transactions can modify data that has been read by the current
transaction until the current transaction completes.

Other transactions cannot insert new rows with key values that would fall
in the range of keys read by any statements in the current transaction until the current transaction completes.
*/
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
BEGIN TRAN
SELECT * FROM Client
WAITFOR DELAY '00:00:10'
SELECT * FROM Client
COMMIT TRAN


-----------------------------
--		  Deadlock		   --
-----------------------------

-- transaction 2 
BEGIN TRAN
	UPDATE Seat SET seat_type='Seat Type Transaction 2' WHERE seat_id=5
	-- this transaction has exclusively lock on table Seat
	WAITFOR DELAY '00:00:10'

	UPDATE Client SET name='New Client Transaction 2' WHERE client_id=1018
	-- this transaction will be blocked because transaction 1 has already blocked our lock on table Client, so, both of the transaction are blocked
COMMIT TRAN


USE [RAILSYSTEM3]
GO
-- [SOLUTION]
-- SET A DEADLOCK PRIORITY 
-- transaction 2
SET DEADLOCK_PRIORITY HIGH
BEGIN TRAN
	UPDATE Seat SET seat_type='Seat Type Transaction 2' WHERE seat_id=5
	-- this transaction has exclusively lock on table Seat
	WAITFOR DELAY '00:00:10'

	UPDATE Client SET name='New Client Transaction 2' WHERE client_id=1018
	-- this transaction has the higher priority level from here (set to HIGH vs NORMAL (trans. 1))
	-- transaction 1 finish with an error, and the results are the ones from this transaction
COMMIT TRAN
