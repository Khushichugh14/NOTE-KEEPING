USE NoteKeeping;

-- Step 1: Add image_url column if it doesn't exist
IF NOT EXISTS (
    SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = 'Note' AND TABLE_NAME = 'notes' AND COLUMN_NAME = 'image_url'
)
BEGIN
    ALTER TABLE Note.notes ADD image_url NVARCHAR(MAX) NULL;
    PRINT 'image_url column added successfully.';
END
ELSE
BEGIN
    PRINT 'image_url column already exists.';
END
GO

-- Step 2: Recreate sp_AddNote with image_url support
CREATE OR ALTER PROCEDURE [Note].[sp_AddNote]
    @title VARCHAR(255),
    @descrpition NVARCHAR(MAX),
    @CreatedByUser INT,
    @category VARCHAR(50) = 'Personal',
    @image_url NVARCHAR(MAX) = NULL
AS
BEGIN
    INSERT INTO Note.notes (title, descrpition, CreatedByUser, category, image_url)
    VALUES (@title, @descrpition, @CreatedByUser, @category, @image_url);
END
GO

-- Step 3: Recreate sp_UpdateNote with image_url support
CREATE OR ALTER PROCEDURE [Note].[sp_UpdateNote]
    @noteID INT,
    @title VARCHAR(255),
    @descrpition NVARCHAR(MAX),
    @category VARCHAR(50) = 'Personal',
    @image_url NVARCHAR(MAX) = NULL
AS
BEGIN
    UPDATE Note.notes
    SET title = @title, descrpition = @descrpition, category = @category, image_url = @image_url, edited_at = GETDATE()
    WHERE noteID = @noteID;
END;
GO

-- Step 4: Recreate sp_GetALLNote to include image_url and category
CREATE OR ALTER PROCEDURE Note.sp_GetALLNote
AS
BEGIN
    SELECT
        n.noteId,
        n.title,
        n.descrpition AS description,
        n.category,
        n.image_url,
        n.created_at AS createdDate,
        n.edited_at AS lastModifiedDate,
        u.userID AS createdByUserID,
        u.userName AS createdByUserName
    FROM Note.notes n
    INNER JOIN Note.users u ON n.CreatedByUser = u.userID;
END;
GO

-- Step 5: Recreate sp_DeleteNote cleanly (no SELECT, no SET NOCOUNT ON)
CREATE OR ALTER PROCEDURE Note.sp_DeleteNote
@noteId INT
AS
BEGIN
    DELETE FROM Note.notes WHERE noteId = @noteId;
END
GO

-- Step 6: Recreate sp_GetUserProfile
CREATE OR ALTER PROCEDURE Note.sp_GetUserProfile
@userID INT
AS
BEGIN
    SELECT 
        userID, userName, email,
        (SELECT COUNT(*) FROM Note.notes WHERE CreatedByUser = @userID) as totalNotes
    FROM Note.users
    WHERE userID = @userID
END
GO

-- Step 7: Recreate sp_UpdateUserProfile (no SELECT at the end)
CREATE OR ALTER PROCEDURE Note.sp_UpdateUserProfile
@userID INT,
@email VARCHAR(100),
@password NVARCHAR(100) = NULL
AS
BEGIN
    IF @password IS NOT NULL AND @password <> ''
    BEGIN
        SET NOCOUNT ON;
        UPDATE Note.users 
        SET email = @email, [password] = HASHBYTES('SHA2_256', @password)
        WHERE userID = @userID
    END
    ELSE
    BEGIN
        SET NOCOUNT ON;
        UPDATE Note.users 
        SET email = @email
        WHERE userID = @userID
    END
END
GO

PRINT 'All migrations completed successfully!';
GO
