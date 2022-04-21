#sql("user.login")
	SELECT
		COUNT(*)
	FROM
		USER
	WHERE
		(
			username = ?
			AND PASSWORD = HEX(
				AES_ENCRYPT(?, "vaehome")
			)
		)
	OR (
		phonenum = ?
		AND PASSWORD = HEX(
			AES_ENCRYPT(?, "vaehome")
		)
	)
	OR (
		email = ?
		AND PASSWORD = HEX(
			AES_ENCRYPT(?, "vaehome")
		)
	);
#end

#sql("user.register")
	INSERT INTO USER (
		id,
		username,
		password,
		role_id,
		locked
	)
	VALUES(	
		next
		VALUE
			FOR MYCATSEQ_USER,
			?,
			HEX(
				AES_ENCRYPT(?, "vaehome")
			),
			"3",
			"0"
	);
#end

#sql("user.searchSummary")
	SELECT
		exp
	FROM
		user_level
	WHERE
		user_id = ?
#end

#sql("user.searchUserID")
	SELECT
		id
	FROM
		`user`
	WHERE
		phonenum = ?
	OR username = ?
	OR email = ?
#end
#sql("user.searchUsername")
	SELECT
		username
	FROM
		`user`
	WHERE
		phonenum = ?
	OR username = ?
	OR email = ?
#end

#sql("user.searchCareNum")
	SELECT
		count(*)
	FROM
		user_care
	WHERE
		user_id = ?
#end

#sql("user.searchFanNum")
	SELECT
		count(*)
	FROM
		user_care
	WHERE
		care_id = ?
#end
#sql("user.searchHot")
	SELECT
		hot
	FROM
		user_hot
	WHERE
		user_id = ?
#end
#sql("user.searchPhoto")
	SELECT
		photo_path
	FROM
		`user`
	WHERE
		id = ?
#end
#sql("user.searchAllPost")
	SELECT
		post.post_detail,
		`user`.photo_path,
		circle.name,
		post.create_time,
		`user`.username,
		post.comment_num,
		post.like_num,
		post.id
	FROM
		post
	INNER JOIN USER ON post.user_id = `user`.id
	INNER JOIN circle ON post.circle = circle.id
	ORDER BY
		post.create_time DESC
#end
#sql("user.getpostID")
	SELECT
		post_id
	FROM
		user_like
	WHERE
		user_id = ?
#end
#sql("user.getPost")
	SELECT
		*
	FROM
		post
	WHERE
		id IN (?)
#end
#sql("user.searchLikePost")
	SELECT
		*
	FROM
		post
	WHERE
		id IN (
			SELECT
				post_id
			FROM
				user_like
			WHERE
				user_id = ?
		)
#end
#sql("user.searchFriendID")
	SELECT
		COUNT(*)
	FROM
		user_care
	WHERE
		user_id IN (
			SELECT
				care_id
			FROM
				user_care
			WHERE
				user_id = ?
		)
	AND care_id = ?
#end
#sql("user.searchComNum")
	SELECT
		count(*)
	FROM
		post_comment
	WHERE
		post_id = ?
#end
#sql("user.updateExp")
	UPDATE 
    	user_level
    SET
    	exp = exp + 10
    WHERE
    	user_id= ?
#end
#sql("user.insertComment")
	INSERT INTO post_comment (
		id,
		post_id,
		user_id,
		comment_detail
	)
	VALUES
		(
			next
			VALUE
				FOR MYCATSEQ_POST_COMMENT,
				?,
				?,
				?
	)
#end
#sql("user.updateCommentNum")
	UPDATE 
    	post
    SET
    	comment_num = comment_num + 1
    WHERE
    	id= ?
#end
#sql("user.updatePostHot")
	UPDATE 
    	post
    SET
    	hot = hot + 5
    WHERE
    	id= ?
#end
#sql("user.updateHot")
	UPDATE 
    	user_hot
    SET
    	hot = hot + 5
    WHERE
    	user_id= ?
#end
#sql("user.searchWriterID")
	SELECT
		user_id
	FROM
		post
	WHERE
		id = ?
#end
#sql("user.loadCommentList")
	SELECT
		`user`.username,
		`user`.photo_path,
		post_comment.comment_detail,
		post_comment.create_time
	FROM
	post_comment
	INNER JOIN USER ON post_comment.user_id = `user`.id
	WHERE
		post_comment.post_id = ?
	ORDER BY
	post_comment.create_time DESC
	
#end
#sql("user.loadthisPost")
	SELECT
		`user`.photo_path,
		`user`.username,
		post.post_detail,
		post.create_time,
		circle. name
	FROM
		post
	INNER JOIN USER ON post.user_id = `user`.id
	INNER JOIN circle ON post.circle = circle.id
	WHERE
		post.id = ?
#end
#sql("user.insertLike")
	INSERT INTO user_like (id, user_id, post_id)
	VALUES
		(
			next
			VALUE
				FOR MYCATSEQ_USER_LIKE,
				?,
				?
		)
#end
#sql("user.insertPost")
	INSERT INTO post (
		id,
		user_id,
		post_detail,
		circle
	)
	VALUES
		(
			next
			VALUE
				FOR MYCATSEQ_POST,
				?,
				?,
				?
	)
#end
#sql("user.searchPostByCircle")
	SELECT
		post.post_detail,
		`user`.photo_path,
		circle.name,
		post.create_time,
		`user`.username,
		post.comment_num,
		post.like_num,
		post.id
	FROM
		post
	INNER JOIN USER ON post.user_id = `user`.id
	INNER JOIN circle ON post.circle = circle.id
	WHERE post.circle= ?
	ORDER BY
		post.create_time DESC
#end
#sql("user.searchAllUser")
	SELECT
		`user`.id,
		`user`.username,
		`user`.photo_path,
		`user`.address,
		user_hot.hot
	FROM
		USER
	INNER JOIN user_hot ON `user`.id = user_hot.user_id
	ORDER BY
		hot DESC
#end
#sql("user.addCare")
	INSERT INTO user_care (id, user_id, care_id)
	VALUES
		(
			next
			VALUE
				FOR MYCATSEQ_USER_CARE,
				?,
			?
		)
#end
#sql("user.addExp")
	INSERT INTO user_level (
			id,
			user_id,
			exp
		)
		VALUES(	
			next
			VALUE
				FOR MYCATSEQ_USER_LEVEL,
			?,
			"0"		
	)
#end
#sql("user.addHot")
	INSERT INTO user_hot (
		id,
		user_id,
		hot
	)
	VALUES(	
		next
		VALUE
			FOR MYCATSEQ_USER_HOT,
			?,
			"0"		
	)
#end
#sql("user.searchPostByUser")
	SELECT
		post.post_detail,
		`user`.photo_path,
		circle.name,
		post.create_time,
		`user`.username,
		post.comment_num,
		post.like_num,
		post.id
	FROM
		post
	INNER JOIN USER ON post.user_id = `user`.id
	INNER JOIN circle ON post.circle = circle.id
	WHERE post.user_id=?
	ORDER BY
		post.create_time DESC
#end
#sql("user.searchPostByHot")
	SELECT
		post.post_detail,
		`user`.photo_path,
		circle.name,
		post.create_time,
		`user`.username,
		post.comment_num,
		post.like_num,
		post.hot,
		post.id
	FROM
		post
	INNER JOIN USER ON post.user_id = `user`.id
	INNER JOIN circle ON post.circle = circle.id
	ORDER BY
		post.hot DESC
#end
#sql("user.searchPostByCare")
	SELECT
		`user`.username,
		post.id,
		post.comment_num,
		post.like_num,
		post.create_time,
		post.post_detail,
		circle. name,
		`user`.photo_path
	FROM
		USER
	INNER JOIN post ON `user`.id = post.user_id
	INNER JOIN user_care ON `user`.id = user_care.care_id
	INNER JOIN circle ON circle.id = post.circle
	WHERE
		user_care.user_id = ?
#end
#sql("user.updateUser")
	UPDATE `user`
	SET sex = ?,
 	address = ?,
 	birthday = ?,
 	email = ?,
 	phonenum = ?
	WHERE
		id = ?
#end
#sql("user.addMessage")
	INSERT INTO message (id, user_id, message)
	VALUES
		(
			next
			VALUE
				FOR MYCATSEQ_MESSAGE,
				?,
				?
		)
#end
#sql("user.searchMessage")
	SELECT
		*
	FROM
		message
	WHERE
		user_id = ?
	ORDER BY
		create_time DESC
#end