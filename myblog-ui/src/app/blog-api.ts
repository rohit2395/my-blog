export class BlogApi{
    public static API_ENDPOINT='http://localhost:8090/api/';

    
    public static AUTH_API_TEST_CONN= BlogApi.API_ENDPOINT + "auth/test";
    public static AUTH_API_REGISTER= BlogApi.API_ENDPOINT + "auth/signup";
    public static AUTH_API_LOGIN= BlogApi.API_ENDPOINT + "auth/login";

    public static POST_API_ADD = BlogApi.API_ENDPOINT + "posts";
    public static POST_API_GET_ALL = BlogApi.API_ENDPOINT + "posts/all"

}