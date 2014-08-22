package org.chibamu.apps.ticketmanager.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import org.chibamu.apps.ticketmanager.model.MediaItem;
import org.chibamu.apps.ticketmanager.model.MediaType;
import org.chibamu.apps.ticketmanager.util.Base64;
import org.chibamu.apps.ticketmanager.util.Reflections;

@RequestScoped
public class MediaManager {
	private static final File tmpDir;
	/**
     * A request scoped cache of computed URLs of media items.
     */
	private final Map<MediaItem, MediaPath> cache;
	static {
		String dataDir = System.getenv("OPENSHIFT_DATA_DIR");
		String parentDir = dataDir != null ? dataDir : System.getProperty("java.io.tmpdir");
		tmpDir = new File (parentDir, "org.chibamu.apps.ticketmanager");
		if(tmpDir.exists()){
			if(tmpDir.isFile())
				throw new IllegalStateException(tmpDir.getAbsolutePath() + 
						" already exists, and is a file");
		}else{
			tmpDir.mkdir();
		}
	}
	public MediaManager() {
		this.cache = new HashMap<MediaItem, MediaPath>();
	}
	/**
	 * Load a cached file by name
	 * @param filename
	 * @return
	 */
	public File getCachedFile(String filename){
		return new File(tmpDir, filename);
	}
	
	/**
     * Compute the URL to a media item. If the media item is not cacheable, then, as long
	 * as the resource can be loaded, the original URL is returned. If the resource is not
	 * available, then a placeholder image replaces it. If the media item is cachable, it
	 * is first cached in the tmp directory, and then path to load it is returned.
     */
	private MediaPath createPath(MediaItem mediaItem) {
        if(mediaItem == null) {
            return createCachedMedia(Reflections.getResource("not_available.jpg").toExternalForm(), MediaType.IMAGE);
        } else if (!mediaItem.getMediaType().isCacheable()) {
            if (checkResourceAvailable(mediaItem)) {
                return new MediaPath(mediaItem.getUrl(), false, mediaItem.getMediaType());
            } else {
                return createCachedMedia(Reflections.getResource("not_available.jpg").toExternalForm(), MediaType.IMAGE);
            }
        } else {
            return createCachedMedia(mediaItem);
        }
    }
	
	/**
	 * Check if a media item can be loaded from it's URL, using the JDK URLConnection classes.
	 * @param mediaItem
	 * @return
	 */
	private boolean checkResourceAvailable(MediaItem mediaItem){
		URL url = null;
		try {
			url = new URL(mediaItem.getUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection.setFollowRedirects(false);
		if(url != null){
			try{
				URLConnection connection = url.openConnection();
				if(connection instanceof HttpURLConnection){
					return ((HttpURLConnection)connection).getResponseCode() == 
								HttpURLConnection.HTTP_OK;
				}else{
					
				}
				
			}catch(IOException e){
				
			}
		}
		return false;
	}
	/**
	 * The cached file name is a base64 encoded version of the URL. This means we don't need to maintain a database of cached
     * files.
	 * @param url
	 * @return
	 */
	private String getCachedFileName(String url){
		return Base64.encodeToString(url.getBytes(), false);
	}
	
	/**
	 * To cache a media item we first load it from the net, then write it to disk.
	 * @param url
	 * @param mediaType
	 * @return
	 */
	private MediaPath createCachedMedia(String url, MediaType mediaType){
		String cachedFileName = getCachedFileName(url);
		if(!alreadyCached(cachedFileName)){
			URL _url = null;
			try{
				_url = new URL(url);
			}catch(MalformedURLException e){
				throw new IllegalStateException("Error reading URL "+url);
			}
			try{
				InputStream is = null;
				OutputStream os = null;
				try {
                    is = new BufferedInputStream(_url.openStream());
                    os = new BufferedOutputStream(getCachedOutputStream(cachedFileName));
                    while (true) {
                        int data = is.read();
                        if (data == -1)
                            break;
                        os.write(data);
                    }
                } finally {
                    if (is != null)
                        is.close();
                    if (os != null)
                        os.close();
                }
			}catch(IOException o){
				throw new IllegalStateException("Error caching "+mediaType.getDescription(),o);
			}
		}
		return new MediaPath(url, true, mediaType);
	}
	
	private MediaPath createCachedMedia(MediaItem mediaItem) {
        return createCachedMedia(mediaItem.getUrl(), mediaItem.getMediaType());
    }
	
	private OutputStream getCachedOutputStream(String fileName) {
        try {
            return new FileOutputStream(getCachedFile(fileName));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Error creating cached file", e);
        }
    }
	/**
	 * Check to see if file is already cached.
	 * @param cachedFileName
	 * @return
	 */
	private boolean alreadyCached(String cachedFileName){
		File cache = getCachedFile(cachedFileName);
		if(cache.exists()){
			if(cache.isDirectory()){
				throw new IllegalStateException(cache.getAbsolutePath()+ " already exists, and is a directory. Remove it.");
			}
			return true;
		}else{
			return false;
		}
	}
	
}
