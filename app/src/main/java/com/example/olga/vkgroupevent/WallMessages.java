package com.example.olga.vkgroupevent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class WallMessages {
    public final Response response;

    @JsonCreator
    public WallMessages(@JsonProperty("response") Response response){
        this.response = response;
    }

    public static final class Response {
        public final long count;
        public final Item items[];

        @JsonCreator
        public Response(@JsonProperty("count") long count, @JsonProperty("items") Item[] items){
            this.count = count;
            this.items = items;
        }

        public static final class Item {
            public final long id;
            public final long from_id;
            public final long owner_id;
            public final long date;
            public final String post_type;
            public final String text;
            public final Long can_edit;
            public final Long created_by;
            public final long can_delete;
            public final Post_source post_source;
            public final Comments comments;
            public final Likes likes;
            public final Reposts reposts;
            public final Long can_pin;

            @JsonCreator
            public Item(@JsonProperty("id") long id, @JsonProperty("from_id") long from_id, @JsonProperty("owner_id") long owner_id, @JsonProperty("date") long date, @JsonProperty("post_type") String post_type, @JsonProperty("text") String text, @JsonProperty(value="can_edit", required=false) Long can_edit, @JsonProperty(value="created_by", required=false) Long created_by, @JsonProperty("can_delete") long can_delete, @JsonProperty("post_source") Post_source post_source, @JsonProperty("comments") Comments comments, @JsonProperty("likes") Likes likes, @JsonProperty("reposts") Reposts reposts, @JsonProperty(value="can_pin", required=false) Long can_pin){
                this.id = id;
                this.from_id = from_id;
                this.owner_id = owner_id;
                this.date = date;
                this.post_type = post_type;
                this.text = text;
                this.can_edit = can_edit;
                this.created_by = created_by;
                this.can_delete = can_delete;
                this.post_source = post_source;
                this.comments = comments;
                this.likes = likes;
                this.reposts = reposts;
                this.can_pin = can_pin;
            }

            public static final class Post_source {
                public final String type;

                @JsonCreator
                public Post_source(@JsonProperty("type") String type){
                    this.type = type;
                }
            }

            public static final class Comments {
                public final long count;
                public final long can_post;

                @JsonCreator
                public Comments(@JsonProperty("count") long count, @JsonProperty("can_post") long can_post){
                    this.count = count;
                    this.can_post = can_post;
                }
            }

            public static final class Likes {
                public final long count;
                public final long user_likes;
                public final long can_like;
                public final long can_publish;

                @JsonCreator
                public Likes(@JsonProperty("count") long count, @JsonProperty("user_likes") long user_likes, @JsonProperty("can_like") long can_like, @JsonProperty("can_publish") long can_publish){
                    this.count = count;
                    this.user_likes = user_likes;
                    this.can_like = can_like;
                    this.can_publish = can_publish;
                }
            }

            public static final class Reposts {
                public final long count;
                public final long user_reposted;

                @JsonCreator
                public Reposts(@JsonProperty("count") long count, @JsonProperty("user_reposted") long user_reposted){
                    this.count = count;
                    this.user_reposted = user_reposted;
                }
            }
        }
    }
}