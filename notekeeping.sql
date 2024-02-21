USE NoteKeeping
GO

CREATE TABLE Note.users (
    userID INT PRIMARY KEY IDENTITY(1,1),
    userName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARBINARY(MAX) ,
	token VARCHAR(MAX),
	time_to_expire  DATETIME
);

--DROP TABLE Note.users

INSERT INTO Note.users (userName, email, password)
VALUES
('john_doe', 'john.doe@example.com', 0x243261243130244d7665354a4866363a546a476862326b444b5939457869587a2f4b672f364d615452353863524c387a444b593945786b634d61543056644364782e6b6a2e4d),
('jane_smith', 'jane.smith@example.com', 0x243261243130243159424a32585a75575a66657848454f654f6548444a516554506f552f4b732f513366366f52307436316d4c5a35507950555a4b716a514a536f396f672e4a4f696d594e504b65),
('mark_johnson', 'mark.johnson@example.com', 0x243261243130243252632e34563079332f6449454979382e6c6d7a727532594e73572f2f4733516636514836764c2e395d352f45425a474669734c395a336e442e492e706739313338586b71),
('emily_wilson', 'emily.wilson@example.com', 0x24326124313024325779333773787a4a5947665830437654382e37572e392f73367044446968767358304c762e786d7a4a5947665830784c7a36),
('david_miller', 'david.miller@example.com', 0x243261243130243757624b6e6c32454e65396f6e625553752f47795a644f39696658366373335834496e4652584271595a49374533424d314f547431756d4758485138315243),
('sarah_turner', 'sarah.turner@example.com', 0x243261243130243577505c7a66455a5931387a74382f44704f484b65576a55626479316d34725a71306e3336694a3431364a68366b57384e74756d324f54673155425a76325642),
('robert_clark', 'robert.clark@example.com', 0x24326124313024367779425848326a6f2e47766135563848526158354f694a78786a636231647335765a3346746e782e2e48667a2e7976694b45416d2e7438546c4a35387936c481),
('lisa_thompson', 'lisa.thompson@example.com', 0x24326124313024375075435a667a45595a313877395336c7c7c69a365b63234e71333034534e7230444d702f49304777562e6d322e6c677a5561396f476a42776a54),
('ryan_baker', 'ryan.baker@example.com', 0x2432612431302438446d63644e466c68584856766f4863416135622e652e4e325357424b6e6c324d35683275722f476c56653048526158354f595a493744352f4642677a2e6e316e33534b47767868),
('olivia_carter', 'olivia.carter@example.com', 0x243261243130243851766d414d576b55705a773845476f75315a5242374f365a63306b6a5a30524f5631396b5036476c6364614133),
('michael_anderson', 'michael.anderson@example.com', 0x24326124313024355536717a6a52504b2e3032713646364b527835764f752f5548711a4a45725372442e54717338664f7968617350795254794271782e76377544326c756),
('emma_harris', 'emma.harris@example.com', 0x2432612431302435305a765732726c2e6b67556635576932655a48666d542e304d442f49704c7352433539424f2f6e304574593552434c6c),
('william_white', 'william.white@example.com', 0x2432612431302444766872664e4c68647856567d5e736f73623272744c4a594a42336c45433575485676364f7a6739744446),
('grace_robinson', 'grace.robinson@example.com', 0x2432612431302439775046756e7547542e6f3057615a5a4f6b437945766a6746424230636a696e636a344c5a4355615146786e4f5a42465565596f6d4665733335),
('alexander_wilkins', 'alexander.wilkins@example.com', 0x2432612431302443644539363753314b3352626b4444745365482e7468383952696c4171444b75777755624a39536d30725a596b4b6169476f41366a4d426830),
('ella_cook', 'ella.cook@example.com', 0x2432612431302444654536433138416431334230314639464a453a3942433938413741423041343138363839303442384b3f),
('james_kelly', 'james.kelly@example.com', 0x24326124313024457a7946314e6c684e4f6352507944474d334a425545464b58554f6f2e3332734e417655736843466d6b),
('amelia_morris', 'amelia.morris@example.com', 0x243261243130244e6f684475496a424e64577436744a6d44774e795579444e4145427438705953304f734366586369303246525f34394a7752416e346a4d38626c383131),
('noah_turner', 'noah.turner@example.com', 0x24326124313024477a7a6845677837324a595366587a4a5947335530776e696a754552426641367366514c576464724f545973),
('ava_parker', 'ava.parker@example.com', 0x243261243130243947644e46536b4741342e4761615730537a385570434d4275793845784453375169506557693274374768);

SELECT * FROM Note.users

CREATE TABLE Note.notes
(
noteId INT PRIMARY KEY IDENTITY(1,1),
title VARCHAR(100),
descrpition NVARCHAR(MAX) ,
created_at DATETIME DEFAULT GETDATE(),
CreatedByUser INT FOREIGN KEY (CreatedByUser) REFERENCES Note.users(userID),
edited_at DATETIME DEFAULT GETDATE(),
active_yn INT DEFAULT 1
);

DROP TABLE Note.notes

INSERT INTO Note.notes (title, descrpition, CreatedByUser, active_yn)
VALUES
('Meeting Notes', 'Notes from the team meeting.', 1, 1),
('Project Ideas', 'Ideas and brainstorming for the new project.', 2, 1),
('Personal Journal', 'Daily reflections and thoughts.', 3, 1),
('Task List', 'List of tasks for the week.', 4, 1),
('Research Notes', 'Notes from research and analysis.', 5, 1),
('Recipe Collection', 'Favorite recipes and cooking notes.', 6, 1),
('Fitness Log', 'Daily workout and fitness tracking.', 7, 1),
('Book Recommendations', 'List of recommended books.', 8, 1),
('Travel Plans', 'Itinerary and plans for upcoming travel.', 9, 1),
('Coding Snippets', 'Useful code snippets and examples.', 10, 1),
('Health Diary', 'Health and wellness tracking.', 11, 1),
('Financial Goals', 'Goals and plans for financial growth.', 12, 1),
('Language Learning', 'Notes from language learning sessions.', 13, 1),
('Music Playlist', 'Favorite songs and music recommendations.', 14, 1),
('Gardening Journal', 'Record of plants and gardening activities.', 15, 1),
('Movie Watchlist', 'Movies to watch in the future.', 16, 1),
('Tech Conference Notes', 'Key takeaways from tech conferences.', 17, 1),
('Home Improvement Ideas', 'Ideas for home improvement projects.', 18, 1),
('Art Sketches', 'Collection of art sketches and drawings.', 19, 1),
('Daily Gratitude', 'Daily moments of gratitude.', 20, 1);


SELECT * FROM Note.notes


/**********
* Store Procedure : Note.sp_AddUser
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to Add User
* Test Code      : EXEC  Note.sp_AddUser  'Harsh' , 'Harsh@gamil.com' , '12345'
**********/

CREATE or ALTER PROCEDURE [Note].[sp_AddUser]
@userName VARCHAR(50),
@email VARCHAR(50),
@password NVARCHAR(100) 
AS
BEGIN
 	
 INSERT INTO Note.users(userName , email , password)
 VALUES( @userName ,@email , HASHBYTES('SHA2_256',@password ))
END;



/**********
* Store Procedure : Note.sp_LoginUser
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to Login User
* Test Code      : EXEC  Note.sp_LoginUser   'Mohanla' , 'Mohan@12345'
**********/
CREATE OR ALTER   PROCEDURE Note.sp_LoginUser 
@userName VARCHAR(30) ,
@password NVARCHAR(MAX)
AS 
BEGIN
	DECLARE @hashedpwd VARBINARY(MAX) =HASHBYTES('SHA2_256', @password)
	DECLARE @count int
	select @count=count(1) from Note.users where userName=@userName and [password]=@hashedpwd
	IF @count=1
	BEGIN
		update Note.users
		SET token = NEWID(),
			time_to_expire = DATEADD(mi ,30, GETDATE())

		select *,1 as validYN
		FROM Note.users where userName=@userName and  [password] =@hashedpwd
	END 
	ELSE
	BEGIN
		select 0 as validYN
	END
END
GO



/**********
* Store Procedure : Note.sp_ValidateToken
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to Validate Token
* Test Code      : EXEC Note.sp_ValidateToken   'A128C7E5-29EA-4790-9F14-726017406AFD' , 4
**********/
CREATE OR ALTER PROCEDURE Note.sp_ValidateToken 
@token VARCHAR(MAX),
@userID INT
AS
BEGIN
	DECLARE @count INT 
	select @count=count(1) from Note.users where userID=@userID and token=@token and time_to_expire > GETDATE()
	IF @count = 1
	BEGIN
		select 1 as ValidYN
	END
	ELSE
	BEGIN
		select 0 as ValidYN
	END
END






/**********
* Store Procedure : Note.sp_AddNote
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to Add Note
* Test Code      : EXEC Note.sp_AddNote 'Educera', 'Web Development and Computitive Programming future of Engineering' , 8
* Revision      : 
**********/
CREATE OR ALTER PROCEDURE [Note].[sp_AddNote]
    @title VARCHAR(255),
    @descrpition NVARCHAR(MAX),
	@CreatedByUser INT
AS
BEGIN
    INSERT INTO Note.notes (title, descrpition, CreatedByUser)
    VALUES (@title, @descrpition, @CreatedByUser);
END
GO

SELECT * FROM Note.notes



/**********
* Store Procedure : Note.sp_fetch_Single_Note
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to fetch single Note
* Test Code      : EXEC Note.sp_fetch_Single_Note 7
* Revision      : 
**********/
CREATE or ALTER  PROCEDURE  Note.sp_fetch_Single_Note
    @NoteId INT
AS
BEGIN
    SELECT
        n.noteId,
        n.title,
        n.descrpition AS description,
        n.created_at AS createdDate,
        n.edited_at AS lastModifiedDate,
        u.userID AS createdByUserID,
        u.userName AS createdByUserName
    FROM
        Note.notes n
    INNER JOIN
        Note.users u ON n.CreatedByUser = u.userID
    WHERE
        n.noteId = @NoteId;
END;



/**********
* Store Procedure : Note.sp_GetALLNote
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to GetALLNote
* Test Code      : EXEC Note.sp_GetALLNote 
* Revision      : 
**********/
CREATE or ALTER PROCEDURE  Note.sp_GetALLNote
AS
BEGIN
    SELECT
        n.noteId,
        n.title,
        n.descrpition AS description,
        n.created_at AS createdDate,
        n.edited_at AS lastModifiedDate,
        u.userID AS createdByUserID,
        u.userName AS createdByUserName
    FROM Note.notes n
    INNER JOIN Note.users u ON 
	n.CreatedByUser = u.userID;
END;



/**********
* Store Procedure : Note.sp_UpdateNote
* Author      : Khushi Chugh
* Date        :  28/01/2024
* Description     : Script to Update  Note
* Test Code      : EXEC Note.sp_UpdateNote  5 , 'Education', 'Web Development and Computitive Programming'
* Revision      : 
**********/
CREATE OR ALTER  PROCEDURE [Note].[sp_UpdateNote]
    @noteID INT,
    @title VARCHAR(255),
    @descrpition NVARCHAR(MAX)
AS
BEGIN
    UPDATE Note.notes
    SET title = @title, descrpition = @descrpition
    WHERE noteID = @noteID;
END;