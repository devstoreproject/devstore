create table banner
(
    id         bigint auto_increment
        primary key,
    image_path varchar(255) null
);

create table cart
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime null,
    last_modified_date datetime null
);

create table hibernate_sequence
(
    next_val bigint null
);

create table item
(
    item_id                     bigint auto_increment
        primary key,
    created_date                datetime     null,
    last_modified_date          datetime     null,
    category                    varchar(255) null,
    delivery_price              int          not null,
    description                 longtext     null,
    discount_rate               int          null,
    item_name                   varchar(255) not null,
    item_price                  int          not null,
    item_status                 varchar(255) null,
    sales_quantity              int          not null,
    view_count                  bigint       not null,
    default_item_item_option_id bigint       null
);

create table item_option
(
    item_option_id   bigint auto_increment
        primary key,
    additional_price int          null,
    default_option   bit          not null,
    item_count       int          null,
    option_detail    varchar(255) null,
    option_name      varchar(255) null,
    item_id          bigint       null,
    constraint FKpiufbijq6m8l18jf9a0nwny62
        foreign key (item_id) references item (item_id)
);

create table cart_item
(
    id         bigint auto_increment
        primary key,
    item_count int    null,
    cart_id    bigint null,
    option_id  bigint null,
    constraint FK1s49ifsqr8e44s4tgiqr8jw3d
        foreign key (option_id) references item_option (item_option_id),
    constraint FK1uobyhgl1wvgt1jpccia8xxs3
        foreign key (cart_id) references cart (id)
);

alter table item
    add constraint FKt438u63no05r6lhomj0fvueyb
        foreign key (default_item_item_option_id) references item_option (item_option_id);

create table item_cart_item_list
(
    item_item_id      bigint not null,
    cart_item_list_id bigint not null,
    constraint UK_p4mcoejdg90wnxkn8y1w15c0c
        unique (cart_item_list_id),
    constraint FK4ma8ktu6nxbasbp5w9sijrown
        foreign key (item_item_id) references item (item_id),
    constraint FKdtyf6d0g77oawx3qvh1mqb2pm
        foreign key (cart_item_list_id) references cart_item (id)
);

create table tag
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table users
(
    id                  bigint auto_increment
        primary key,
    created_date        datetime     null,
    last_modified_date  datetime     null,
    email               varchar(255) null,
    grade               varchar(255) null,
    last_connected_date datetime     null,
    nick_name           varchar(255) null,
    password            varchar(255) null,
    phone               varchar(255) null,
    profile_image       varchar(255) null,
    provider_id         varchar(255) null,
    user_name           varchar(255) null,
    user_role           varchar(255) null,
    user_status         varchar(255) null,
    cart_id             bigint       null,
    constraint FKqmifheg6lnigfifvlmpjnuny8
        foreign key (cart_id) references cart (id)
);

create table answer
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    comment            longtext     null,
    qna_status         varchar(255) null,
    user_id            bigint       null,
    constraint FKsdj8jab9t00diflkysw22k7bv
        foreign key (user_id) references users (id)
);

create table notice
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    content            longtext     null,
    notice_category    varchar(255) null,
    title              varchar(255) null,
    view_count         bigint       not null,
    user_id            bigint       null,
    constraint FK6hu3mfrsmpbqjk44w2fq5t5us
        foreign key (user_id) references users (id)
);

create table orders
(
    order_id           bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    address_detail     varchar(255) null,
    address_simple     varchar(255) null,
    phone              varchar(255) null,
    zip_code           varchar(255) null,
    delivery_company   varchar(255) null,
    delivery_date      date         null,
    delivery_price     int          not null,
    message            varchar(255) null,
    order_number       varchar(255) null,
    orders_status      varchar(255) null,
    recipient          varchar(255) null,
    tracking_number    varchar(255) null,
    user_id            bigint       null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (id)
);

create table ordered_item
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    discount_rate      int          not null,
    discounted_price   int          not null,
    item_count         int          not null,
    item_details       varchar(255) null,
    option_details     varchar(255) null,
    price              int          not null,
    item_item_id       bigint       null,
    item_option_id     bigint       null,
    order_id           bigint       null,
    constraint FK51dj38kcdahtjcx8h2be767sr
        foreign key (order_id) references orders (order_id),
    constraint FK75bf4yr0aurqjj6tw30l24grw
        foreign key (item_item_id) references item (item_id),
    constraint FKhckgj4xm76hs7vgu1c39fb1iq
        foreign key (item_option_id) references item_option (item_option_id)
);

create table picked_item
(
    picked_id bigint auto_increment
        primary key,
    item_id   bigint null,
    user_id   bigint null,
    constraint FK412aa36e782r9cwskhp07b12a
        foreign key (user_id) references users (id),
    constraint FKprhy3ousui85b0gs84e7qt6t2
        foreign key (item_id) references item (item_id)
);

create table question
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    comment            longtext     null,
    qna_status         varchar(255) null,
    answer_id          bigint       null,
    item_id            bigint       null,
    user_id            bigint       null,
    constraint FK2w9qd6mx9oh2vchntaokhlj4f
        foreign key (answer_id) references answer (id),
    constraint FK7rnpup7eaonh2ubt922ormoij
        foreign key (user_id) references users (id),
    constraint FKjramekl8wg1qjhqixc509iwlq
        foreign key (item_id) references item (item_id)
);

create table review
(
    id                 bigint       not null
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    best               bit          not null,
    comment            varchar(255) null,
    rating             int          null,
    item_id            bigint       null,
    user_id            bigint       null,
    constraint FK6cpw2nlklblpvc7hyt7ko6v3e
        foreign key (user_id) references users (id),
    constraint FK6hb6qqehnsm7mvfgv37m66hd7
        foreign key (item_id) references item (item_id)
);

create table image
(
    category           varchar(31)  not null,
    id                 bigint auto_increment
        primary key,
    created_date       datetime     null,
    last_modified_date datetime     null,
    ext                varchar(255) null,
    hash               varchar(255) null,
    image_order        int          not null,
    image_path         varchar(255) null,
    is_representative  bit          not null,
    original_name      varchar(255) null,
    thumbnail_path     varchar(255) null,
    upload_name        varchar(255) null,
    item_id            bigint       null,
    notice_id          bigint       null,
    review_id          bigint       null,
    constraint FK2fyo6jg4fpq9108cbi16v37ft
        foreign key (review_id) references review (id),
    constraint FKmxefv2q9erynjpr2u4tftmn1r
        foreign key (notice_id) references notice (id),
    constraint FKscew1f5bnpn1nuaokhjg89u58
        foreign key (item_id) references item (item_id)
);

create table likes
(
    id                 bigint auto_increment
        primary key,
    created_date       datetime null,
    last_modified_date datetime null,
    review_id          bigint   null,
    user_id            bigint   null,
    constraint FK65pxk1tcl3qtbvlcrjyitq865
        foreign key (review_id) references review (id),
    constraint FKnvx9seeqqyy71bij291pwiwrg
        foreign key (user_id) references users (id)
);

create table shipping_info
(
    shipping_info  bigint auto_increment
        primary key,
    address_detail varchar(255) null,
    address_simple varchar(255) null,
    phone          varchar(255) null,
    zip_code       varchar(255) null,
    recipient      varchar(255) null,
    order_id       bigint       null,
    user_id        bigint       null,
    constraint FK45idk6acnnfditxgyyk613m6r
        foreign key (order_id) references orders (order_id),
    constraint FKokw6bplftwitsoh1utl05eyk8
        foreign key (user_id) references users (id)
);

