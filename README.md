<p align = "center">
    <img src = "assets-readme/Sparta_Coding_Club.png" width = 150 />
    <img src = "assets-readme/Kotlin_Spring_2nd.png" width = 150 />
    <img src = "assets-readme/E1I4.png" width = 150 />
</p>

# `MusicNewsFeed`


# 개발 환경

| 기준  | 내용                                                                                                  |
|-----|-----------------------------------------------------------------------------------------------------|
| IDE | `IntelliJ IDEA 2024.1`                                                                              |
| SDK | 개발 언어: `Kotlin 1.9.23`(JVM: `OpenJDK 17.0.3`)<br/>프레임워크: `Spring Boot 3.3.0`<br/>빌드 툴: `Gradle 8.7` |

# Entity-Relationship Diagram (ERD)

```mermaid
%%{ init: { 'theme': 'dark' } }%%
        
erDiagram
Member {
        id bigint PK
        username text
        password text
}
Post {
        id bigint PK
        title text
        member_id bigint
        content text
        music_url text
        tags text
        music_type_id text
        view_count bigint
        post_status text
        upvote_count bigint
        report_count bigint
        comment_count bigint
        created_at timestamptz
        updated_at timestamptz
}
MusicType {
        type text PK
        count_post bigint
}
Comment {
        id bigint PK
        content text
        member_id text
        post_id bigint FK
        created_at timestamptz
        updated_at timestamptz
}
Upvote {
        id bigint PK
        member_id bigint FK
        post_id bigint FK
}
Report {
        id bigint PK
        member_id bigint FK
        post_id bigint FK
}
Follow {
        id bigint PK
        member_id bigint
        follower_member_id bigint
}

Member ||--o{ Report: "can make"
Report }o--|| Post: "can be at"
Member ||--o{ Upvote: "can make"
Upvote }o--|| Post: "can be at"
Post ||--o{ Comment: "can have"
```

# [API Call](https://cold-cover-56c.notion.site/77df80041ace4ce4aea678c03e318c7f?v=4aa3945b53384c19adcd91f6918eeeeb)
![MusicNewsFeed-API_Call.png](assets-readme/MusicNewsFeed-API_Call.png)