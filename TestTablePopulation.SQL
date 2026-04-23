-- Code was made with ai assistance. Some edits where made to make easy testing of the application.

DELETE FROM CHECKOUT;
DELETE FROM MEMBER;
DELETE FROM BOOK;

INSERT INTO BOOK (ISBN, TITLE, GENRE, AUTHOR) VALUES
	(9780134685991, 'Effective Java', 'Programming', 'Joshua Bloch'),
	(9780132350884, 'Clean Code', 'Programming', 'Robert C. Martin'),
	(9781491950357, 'Designing Data-Intensive Applications', 'Technology', 'Martin Kleppmann'),
	(9780262033848, 'Introduction to Algorithms', 'Education', 'Thomas H. Cormen'),
	(9780596009205, 'Head First Java', 'Programming', 'Kathy Sierra'),
	(9780134494166, 'Clean Architecture', 'Programming', 'Robert C. Martin'),
	(9781617294945, 'Spring in Action', 'Technology', 'Craig Walls'),
	(9780201633610, 'Design Patterns', 'Programming', 'Erich Gamma'),
	(9780321356680, 'Effective C++', 'Programming', 'Scott Meyers'),
	(9781449355739, 'Learning Python', 'Programming', 'Mark Lutz'),
	(9780061120084, 'To Kill a Mockingbird', 'Fiction', 'Harper Lee'),
	(9780743273565, 'The Great Gatsby', 'Fiction', 'F. Scott Fitzgerald'),
	(1111111111111, 'Test Book One', 'Fiction', 'Test Author'),
	(2222222222222, 'Test Book Two', 'Programming', 'Test Author'),
	(3333333333333, 'Test Book Three', 'Technology', 'Test Author'),
	(4444444444444, 'Test Book Four', 'Education', 'Test Author'),
	(5555555555555, 'Test Book Five', 'Fiction', 'Test Author');

INSERT INTO MEMBER (FIRSTNAME, LASTNAME) VALUES
	('Alice', 'Nguyen'),
	('Marcus', 'Lee'),
	('Priya', 'Patel'),
	('Jordan', 'Smith'),
	('Emily', 'Chen'),
	('Noah', 'Brown'),
	('Sophia', 'Garcia'),
	('Daniel', 'Wilson'),
	('Olivia', 'Taylor'),
	('Liam', 'Anderson'),
	('Alice', 'Smith'),
	('Marcus', 'Nguyen'),
	('Test', 'User'),
	('Test', 'User'),
	('Test', 'User');

INSERT INTO CHECKOUT (CHECKOUTDATE, RETURNDATE, MEMBERID, BOOKISBN) VALUES
	('2026-04-16', '2026-04-23', 1, 9780134685991),
	('2026-04-18', '2026-04-25', 2, 9780132350884),
	('2026-04-20', '2026-04-27', 3, 9781491950357),
	('2026-04-10', '2026-04-17', 4, 9780262033848),
	('2026-04-12', '2026-04-19', 5, 9780596009205),
	('2026-04-14', '2026-04-21', 6, 9780134494166),
	('2026-04-15', '2026-04-22', 7, 9781617294945),
	('2026-04-17', '2026-04-24', 8, 9780201633610),
	('2026-04-19', '2026-04-26', 9, 9780321356680),
	('2026-04-21', '2026-04-28', 10, 9781449355739),
	('2026-04-22', '2026-04-29', 11, 1111111111111),
	('2026-04-22', '2026-04-29', 12, 2222222222222),
	('2026-04-23', '2026-04-30', 13, 3333333333333),
	('2026-04-23', '2026-04-30', 14, 4444444444444),
	('2026-04-23', '2026-04-30', 15, 5555555555555),
	('2026-04-01', '2026-04-08', 1, 1111111111111),
	('2026-04-02', '2026-04-09', 2, 2222222222222);
