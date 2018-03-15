package interview.blog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import interview.blog.service.BlogPostService;
import interview.blog.service.BlogPostServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

/**
 * TODO: Implement an interface to the database, capable of storing, retrieving,
 * and deleting blog posts.
 * 
 * You can choose the database (SQLites, MongoDB, PostgreSQL, or whatever) and
 * the interface method (Hibernate, hand-written SQL, etc).
 */
public class BlogDB  {
    private PegDownProcessor pegdown = new PegDownProcessor( Extensions.ALL );

    private BlogPostService postService = BlogPostServiceImpl.getInstance();
    
    /**
     * Creates of updates a blog post, and returns its UUID.
     * @param post the blog post
     * @return the UUID
     */
    public String store( BlogPost post ){
        if( StringUtils.isBlank( post.getUuid() ) ){
            post.setUuid( UUID.randomUUID().toString() );
        } 
        else {
            postService.remove(post.getUuid());
        }
        
        if( post.getPubDate() == 0 ){
            post.setPubDate( new Date().getTime() );
        }
        
        String pull = post.getPullQuote();

        if( !StringUtils.isBlank( pull ) ){
            pull = pegdown.markdownToHtml( pull );
            post.setPullQuoteAsHtml( pull );
        }
        
        String body = post.getBody();

        if( !StringUtils.isBlank( body ) ){
            body = pegdown.markdownToHtml( body );
            post.setBodyAsHtml( body );
        }

        com.galvin.interview.entity.BlogPost savePost = convert(post);
        postService.save(savePost);

        return savePost.getUuid();
    }
    
    /**
     * Deletes any blog post with the given UUID.
     * @param uuid 
     */
    public void delete( String uuid ){
        postService.remove(uuid);
    }
    
    /**
     * Retrieves a single blog post based on the UUID.
     * @param uuid the UUID of the target blog post
     * @return the blog post
     */
    public BlogPost get( String uuid ) {
        com.galvin.interview.entity.BlogPost foundPost = postService.findByUUID(uuid);

        BlogPost result = null;

        if (foundPost != null) {
            result = convert(foundPost);
            result = result.clone();
        }

        return result;
    }
    
    /**
     * Retrieves all blog posts in the database.
     * @return all blog posts in the database.
     */
    public List<BlogPost> get(){
        List<BlogPost> result = new ArrayList<>();

        List<com.galvin.interview.entity.BlogPost> foundPosts = postService.findAll();

        for (com.galvin.interview.entity.BlogPost blogPost : foundPosts) {
            BlogPost post = convert(blogPost);
            post = post.clone();
            result.add(post);
        }
        
        result.sort(new BlogPostComparator());

        return result;
    }

    private com.galvin.interview.entity.BlogPost convert(final BlogPost post) {
        final String uuid = post.getUuid();
        final String title = post.getTitle();
        final long pubDate = post.getPubDate();
        final String author = post.getAuthor();
        final String authorEmail = post.getAuthorEmail();
        final String pullQuote = post.getPullQuote();
        final String pullQuoteAsHTML = post.getPullQuoteAsHtml();
        final String body = post.getBody();
        final String bodyAsHTML = post.getBodyAsHtml();

        com.galvin.interview.entity.BlogPost newPost = new com.galvin.interview.entity.BlogPost();
        newPost.setUuid(uuid);
        newPost.setTitle(title);
        newPost.setPubDate(pubDate);
        newPost.setAuthor(author);
        newPost.setAuthorEmail(authorEmail);
        newPost.setPullQuote(pullQuote);
        newPost.setPullQuoteAsHtml(pullQuoteAsHTML);
        newPost.setBody(body);
        newPost.setBodyAsHtml(bodyAsHTML);

        return newPost;
    }

    private BlogPost convert(final com.galvin.interview.entity.BlogPost post) {
        final String uuid = post.getUuid();
        final String title = post.getTitle();
        final long pubDate = post.getPubDate();
        final String author = post.getAuthor();
        final String authorEmail = post.getAuthorEmail();
        final String pullQuote = post.getPullQuote();
        final String pullQuoteAsHTML = post.getPullQuoteAsHtml();
        final String body = post.getBody();
        final String bodyAsHTML = post.getBodyAsHtml();

        BlogPost newPost = new BlogPost();
        newPost.setUuid(uuid);
        newPost.setTitle(title);
        newPost.setPubDate(pubDate);
        newPost.setAuthor(author);
        newPost.setAuthorEmail(authorEmail);
        newPost.setPullQuote(pullQuote);
        newPost.setPullQuoteAsHtml(pullQuoteAsHTML);
        newPost.setBody(body);
        newPost.setBodyAsHtml(bodyAsHTML);

        return newPost;
    }
}
