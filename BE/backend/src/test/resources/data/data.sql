-- 사용자 등록 admin 계정 1개 client 계정 1개
insert into  cart
(created_date, last_modified_date)
values
    (now(), now());
insert into users
(created_date, last_modified_date, cart_id, email, grade, last_connected_date, nick_name, password, phone, profile_image, provider_id, user_name, user_role, user_status)
values
    (now(),now(),1,'client@gmail.com','NORMAL',now(),'김클라','{bcrypt}$2a$10$UMwSaoJW2v9aC4QAF8HqpOOEiVcU12jLNJrZSakG3PKVIXWNSG/v.','010-1234-1234','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU','JWT','김송모','CLIENT','TMP');

insert into  cart
(created_date, last_modified_date)
values
    (now(), now());
insert into users
(created_date, last_modified_date, cart_id, email, grade, last_connected_date, nick_name, password, phone, profile_image, provider_id, user_name, user_role, user_status)
values
    (now(),now(),2,'admin@gmail.com','NORMAL',now(),'김어드','{bcrypt}$2a$10$UMwSaoJW2v9aC4QAF8HqpOOEiVcU12jLNJrZSakG3PKVIXWNSG/v.','010-1234-1234','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU','JWT','김송모','ADMIN','TMP');
-- 상품 등록 2개 각 상품은 옵션 2개 이미지 2개 보유

insert
into
    item_option
(additional_price, default_option, item_id, item_count, option_detail, option_name)
values
    (0, true, null, 1000, null, null);


    insert
    into
        item
        (created_date, last_modified_date, category, default_item_item_option_id, delivery_price, description, discount_rate, item_name, item_price, item_status, sales_quantity, view_count)
    values
        (now(), now(), 'COMPUTER', 1, 3000, '상품에 대한 상세 설명 1', 0, '맥북 1', 1000000, 'ON_STACK', 0, 0);

    insert
    into
        image
        (created_date, last_modified_date, ext, hash, image_order, image_path, is_representative, original_name, thumbnail_path, upload_name, item_id, category)
    values
        (now(), now(), '.png', 'aabacd36781f70c6a844bf07835299d2', 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', false, '스크린샷', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', 'random001', 1, 'ITEM');

insert
into
    image
(created_date, last_modified_date, ext, hash, image_order, image_path, is_representative, original_name, thumbnail_path, upload_name, item_id, category)
values
    (now(), now(), '.png', 'aabacd36781f70c6a844bf07835299d2', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', true, '스크린샷', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', 'random001', 1, 'ITEM');


    insert
    into
        item_option
        (additional_price, default_option, item_id, item_count, option_detail, option_name)
    values
        (10000, false, 1, 100, '상품 상세 정보 1', null);
insert
into
    item_option
(additional_price, default_option, item_id, item_count, option_detail, option_name)
values
    (20000, false, 1, 200, '상품 상세 정보 2', null);

update
    item_option
set
    additional_price=0,
    default_option=true,
    item_id=1,
    item_count=1000,
    option_detail=null,
    option_name=null
where
        item_option_id=1;

-- 경계점
insert
into
    item_option
(additional_price, default_option, item_id, item_count, option_detail, option_name)
values
    (0, true, null, 1000, null, null);

    insert
    into
        item
        (created_date, last_modified_date, category, default_item_item_option_id, delivery_price, description, discount_rate, item_name, item_price, item_status, sales_quantity, view_count)
    values
        (now(), now(), 'COMPUTER', 4, 3000, '상품에 대한 상세 설명 1', 0, '맥북 1', 1000000, 'ON_STACK', 0, 0);

    insert
    into
        image
        (created_date, last_modified_date, ext, hash, image_order, image_path, is_representative, original_name, thumbnail_path, upload_name, item_id, category)
    values
        (now(), now(), '.png', 'aabacd36781f70c6a844bf07835299d2', 0, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', false, '스크린샷', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', 'random0011', 2, 'ITEM');

insert
into
    image
(created_date, last_modified_date, ext, hash, image_order, image_path, is_representative, original_name, thumbnail_path, upload_name, item_id, category)
values
    (now(), now(), '.png', 'aabacd36781f70c6a844bf07835299d2', 1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', true, '스크린샷', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz7rUlbKGMcXKcNkWGw6CnN_CRBz1hYjrsKXFVio9s26u7nQEILnX8EGV8e5UEIdGdsI0&usqp=CAU', 'random0022', 2, 'ITEM');


    insert
    into
        item_option
        (additional_price, default_option, item_id, item_count, option_detail, option_name)
    values
        (10000, false, 2, 100, '상품 상세 정보 1', null);
insert
into
    item_option
(additional_price, default_option, item_id, item_count, option_detail, option_name)
values
    (20000, false, 2, 200, '상품 상세 정보 2', null);

update
    item_option
set
    additional_price=0,
    default_option=true,
    item_id=2,
    item_count=1000,
    option_detail=null,
    option_name=null
where
        item_option_id=4;

--  리뷰 2개
insert
into
    review
(created_date, last_modified_date, best, comment, item_id, rating, user_id,id)
values
    (now(), now(), false, '상품 리뷰 본문1', 1, 10, 1,1);
insert
into
    review
(created_date, last_modified_date, best, comment, item_id, rating, user_id,id)
values
    (now(), now(), false, '상품 리뷰 본문2', 1, 10, 1,2);
insert
into
    review
(created_date, last_modified_date, best, comment, item_id, rating, user_id,id)
values
    (now(), now(), false, '상품 리뷰 본문3', 2, 10, 1,3);
insert
into
    review
(created_date, last_modified_date, best, comment, item_id, rating, user_id,id)
values
    (now(), now(), false, '상품 리뷰 본문4', 2, 10, 1,4);

-- QNA 2개
insert
into
    question
(created_date, last_modified_date, answer_id, comment, item_id, qna_status, user_id)
values
    (now(), now(), null, '질문 본문1', 1, 'REGISTER', 1);

insert
into
    question
(created_date, last_modified_date, answer_id, comment, item_id, qna_status, user_id)
values
    (now(), now(), null, '질문 본문2', 1, 'REGISTER', 1);
insert
into
    question
(created_date, last_modified_date, answer_id, comment, item_id, qna_status, user_id)
values
    (now(), now(), null, '질문 본문3', 2, 'REGISTER', 1);
insert
into
    question
(created_date, last_modified_date, answer_id, comment, item_id, qna_status, user_id)
values
    (now(), now(), null, '질문 본문4', 2, 'REGISTER', 1);

insert
into
    answer
(created_date, last_modified_date, comment, qna_status, user_id)
values
    (now(), now(), '답변 본문 1', 'ANSWER_COMPLETE', 2);
update
    question
set
    last_modified_date=now(),
    answer_id=1,
    comment='질문 본문1',
    item_id=1,
    qna_status='ANSWER_COMPLETE',
    user_id=1
where
        id=1;

insert
into
    answer
(created_date, last_modified_date, comment, qna_status, user_id)
values
    (now(), now(), '답변 본문 2', 'ANSWER_COMPLETE', 2);
update
    question
set
    last_modified_date=now(),
    answer_id=2,
    comment='질문 본문2',
    item_id=2,
    qna_status='ANSWER_COMPLETE',
    user_id=1
where
        id=3;

-- Notice 2개
insert
into
    notice
(created_date, last_modified_date, content, notice_category, title, user_id, view_count)
values
    (now(), now(), '공지 본문 1', 'EVENT', '공지 제목 1', 2, 0);

insert
into
    image
(created_date, last_modified_date, ext, hash, image_order, image_path, is_representative, original_name, thumbnail_path, upload_name, notice_id, category)
values
    (now(), now(), '.png', 'f239r0f3f3223fr', 0, '이미지 파일 경로', true, '이미지파일', '섬네일 경로', 'f23434f', 1, 'NOTICE');
insert
into
    notice
(created_date, last_modified_date, content, notice_category, title, user_id, view_count)
values
    (now(), now(), '공지 본문 2', 'EVENT', '공지 제목 2', 2, 0);

insert
into
    image
(created_date, last_modified_date, ext, hash, image_order, image_path, is_representative, original_name, thumbnail_path, upload_name, notice_id, category)
values
    (now(), now(), '.png', 'f239r0f3f3223fr', 0, '이미지 파일 경로2', true, '이미지파일2', '섬네일 경로2', 'f23434f', 2, 'NOTICE');


