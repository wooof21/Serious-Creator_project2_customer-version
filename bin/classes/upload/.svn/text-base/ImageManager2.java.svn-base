package upload;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * 图片加载�?
 * 
 * @author 月月�?
 */
public class ImageManager2 {

    private static ImageManager2 imageManager;
    public LruCache<String, Bitmap> mMemoryCache;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 20; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";
    public DiskLruCache mDiskCache;
    private static Application myapp;

    /** 图片加载队列，后进先�? */
    private Stack<ImageRef> mImageQueue = new Stack<ImageRef>();

    /** 图片请求队列，先进先出，用于存放已发送的请求�? */
    private Queue<ImageRef> mRequestQueue = new LinkedList<ImageRef>();

    /** 图片加载线程消息处理�? */
    private Handler mImageLoaderHandler;

    /** 图片加载线程是否就绪 */
    private boolean mImageLoaderIdle = true;

    /** 请求图片 */
    private static final int MSG_REQUEST = 1;
    /** 图片加载完成 */
    private static final int MSG_REPLY = 2;
    /** 中止图片加载线程 */
    private static final int MSG_STOP = 3;
    /** 如果图片是从网络加载，则应用渐显动画，如果从缓存读出则不应用动画 */
    private boolean isFromNet = true;

    /**
     * 获取单例，只能在UI线程中使用�??
     * 
     * @param context
     * @return
     */
    public static ImageManager2 from(Context context) {

	// 如果不在ui线程中，则抛出异�?
	if (Looper.myLooper() != Looper.getMainLooper()) {
	    throw new RuntimeException("Cannot instantiate outside UI thread.");
	}

	if (myapp == null) {
	    myapp = (Application) context.getApplicationContext();
	}

	if (imageManager == null) {
	    imageManager = new ImageManager2(myapp);
	}

	return imageManager;
    }

    /**
     * 私有构�?�函数，保证单例模式
     * 
     * @param context
     */
    private ImageManager2(Context context) {
	int memClass = ((ActivityManager) context
		.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
	memClass = memClass > 32 ? 32 : memClass;
	// 使用可用内存�?1/8作为图片缓存
	final int cacheSize = 1024 * 1024 * memClass / 8;

	mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

	    protected int sizeOf(String key, Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	    }

	};

	File cacheDir = DiskLruCache
		.getDiskCacheDir(context, DISK_CACHE_SUBDIR);
	mDiskCache = DiskLruCache.openCache(context, cacheDir, DISK_CACHE_SIZE);

    }

    /**
     * 存放图片信息
     */
    class ImageRef {

	/** 图片对应ImageView控件 */
	ImageView imageView;
	/** 图片URL地址 */
	String url;
	/** 图片缓存路径 */
	String filePath;
	/** 默认图资源ID */
	int resId;
	int width = 0;
	int height = 0;

	/**
	 * 构�?�函�?
	 * 
	 * @param imageView
	 * @param url
	 * @param resId
	 * @param filePath
	 */
	ImageRef(ImageView imageView, String url, String filePath, int resId) {
	    this.imageView = imageView;
	    this.url = url;
	    this.filePath = filePath;
	    this.resId = resId;
	}

	ImageRef(ImageView imageView, String url, String filePath, int resId,
		int width, int height) {
	    this.imageView = imageView;
	    this.url = url;
	    this.filePath = filePath;
	    this.resId = resId;
	    this.width = width;
	    this.height = height;
	}

    }

    /**
     * 显示图片
     * 
     * @param imageView
     * @param url
     * @param resId
     */
    public void displayImage(ImageView imageView, String url, int resId) {
	if (imageView == null) {
	    return;
	}
	if (imageView.getTag() != null
		&& imageView.getTag().toString().equals(url)) {
	    return;
	}
	if (resId >= 0) {
	    if (imageView.getBackground() == null) {
		imageView.setBackgroundResource(resId);
	    }
	    imageView.setImageDrawable(null);

	}
	if (url == null || url.equals("")) {
	    return;
	}

	// 添加url tag
	imageView.setTag(url);

	// 读取map缓存
	Bitmap bitmap = mMemoryCache.get(url);
	if (bitmap != null) {
	    setImageBitmap(imageView, bitmap, false);
	    return;
	}

	// 生成文件�?
	String filePath = urlToFilePath(url);
	if (filePath == null) {
	    return;
	}

	queueImage(new ImageRef(imageView, url, filePath, resId));
    }

    /**
     * 显示图片固定大小图片的缩略图，一般用于显示列表的图片，可以大大减小内存使�?
     * 
     * @param imageView
     *            加载图片的控�?
     * @param url
     *            加载地址
     * @param resId
     *            默认图片
     * @param width
     *            指定宽度
     * @param height
     *            指定高度
     */
    public void displayImage(ImageView imageView, String url, int resId,
	    int width, int height) {
	if (imageView == null) {
	    return;
	}
	if (resId >= 0) {

	    if (imageView.getBackground() == null) {
		imageView.setBackgroundResource(resId);
	    }
	    imageView.setImageDrawable(null);

	}
	if (url == null || url.equals("")) {
	    return;
	}

	// 添加url tag
	imageView.setTag(url);
	// 读取map缓存
	Bitmap bitmap = mMemoryCache.get(url + width + height);
	if (bitmap != null) {
	    setImageBitmap(imageView, bitmap, false);
	    return;
	}

	// 生成文件�?
	String filePath = urlToFilePath(url);
	if (filePath == null) {
	    return;
	}

	queueImage(new ImageRef(imageView, url, filePath, resId, width, height));
    }

    /**
     * 入队，后进先�?
     * 
     * @param imageRef
     */
    public void queueImage(ImageRef imageRef) {

	// 删除已有ImageView
	Iterator<ImageRef> iterator = mImageQueue.iterator();
	while (iterator.hasNext()) {
	    if (iterator.next().imageView == imageRef.imageView) {
		iterator.remove();
	    }
	}

	// 添加请求
	mImageQueue.push(imageRef);
	sendRequest();
    }

    /**
     * 发�?�请�?
     */
    private void sendRequest() {

	// �?启图片加载线�?
	if (mImageLoaderHandler == null) {
	    HandlerThread imageLoader = new HandlerThread("image_loader");
	    imageLoader.start();
	    mImageLoaderHandler = new ImageLoaderHandler(
		    imageLoader.getLooper());
	}

	// 发�?�请�?
	if (mImageLoaderIdle && mImageQueue.size() > 0) {
	    ImageRef imageRef = mImageQueue.pop();
	    Message message = mImageLoaderHandler.obtainMessage(MSG_REQUEST,
		    imageRef);
	    mImageLoaderHandler.sendMessage(message);
	    mImageLoaderIdle = false;
	    mRequestQueue.add(imageRef);
	}
    }

    /**
     * 图片加载线程
     */
    class ImageLoaderHandler extends Handler {

	public ImageLoaderHandler(Looper looper) {
	    super(looper);
	}

	public void handleMessage(Message msg) {
	    if (msg == null)
		return;

	    switch (msg.what) {

	    case MSG_REQUEST: // 收到请求
		Bitmap bitmap = null;
		Bitmap tBitmap = null;
		if (msg.obj != null && msg.obj instanceof ImageRef) {

		    ImageRef imageRef = (ImageRef) msg.obj;
		    String url = imageRef.url;
		    if (url == null)
			return;
		    // 如果本地url即读取sd相册图片，则直接读取，不用经过DiskCache
		    // if (url.toLowerCase().contains("dcim")) {

		    tBitmap = null;
		    BitmapFactory.Options opt = new BitmapFactory.Options();
		    opt.inSampleSize = 1;
		    opt.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(url, opt);
		    int bitmapSize = opt.outHeight * opt.outWidth * 4;
		    opt.inSampleSize = bitmapSize / (1000 * 2000);
		    opt.inJustDecodeBounds = false;
		    tBitmap = BitmapFactory.decodeFile(url, opt);
		    if (imageRef.width != 0 && imageRef.height != 0) {
			bitmap = ThumbnailUtils.extractThumbnail(tBitmap,
				imageRef.width, imageRef.height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			isFromNet = true;
		    } else {
			bitmap = tBitmap;
			tBitmap = null;
		    }

		    // }
		    // else
		    // bitmap = mDiskCache.get(url);

		    if (bitmap != null) {
			// ToolUtil.log("从disk缓存读取");
			// 写入map缓存
			if (imageRef.width != 0 && imageRef.height != 0) {
			    if (mMemoryCache.get(url + imageRef.width
				    + imageRef.height) == null)
				mMemoryCache.put(url + imageRef.width
					+ imageRef.height, bitmap);
			} else {
			    if (mMemoryCache.get(url) == null)
				mMemoryCache.put(url, bitmap);
			}

		    } else {
			// try {
			// byte[] data = loadByteArrayFromNetwork(url);
			//
			// if (data != null) {
			//
			// BitmapFactory.Options opt = new
			// BitmapFactory.Options();
			// opt.inSampleSize = 1;
			//
			// opt.inJustDecodeBounds = true;
			// BitmapFactory.decodeByteArray(data, 0, data.length,
			// opt);
			// int bitmapSize = opt.outHeight * opt.outWidth * 4;//
			// pixels*3
			// // if
			// // it's
			// // RGB
			// // and
			// // pixels*4
			// // if
			// // it's
			// // ARGB
			// if (bitmapSize > 1000 * 1200)
			// opt.inSampleSize = 2;
			// opt.inJustDecodeBounds = false;
			// tBitmap = BitmapFactory.decodeByteArray(data, 0,
			// data.length, opt);
			// if (imageRef.width != 0 && imageRef.height != 0) {
			// bitmap = ThumbnailUtils.extractThumbnail(tBitmap,
			// imageRef.width, imageRef.height,
			// ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			// } else {
			// bitmap = tBitmap;
			// tBitmap = null;
			// }
			//
			// if (bitmap != null && url != null) {
			// // 写入SD�?
			// if (imageRef.width != 0 && imageRef.height != 0) {
			// mDiskCache.put(url + imageRef.width +
			// imageRef.height,
			// bitmap);
			// mMemoryCache.put(url + imageRef.width +
			// imageRef.height,
			// bitmap);
			// } else {
			// mDiskCache.put(url, bitmap);
			// mMemoryCache.put(url, bitmap);
			// }
			// isFromNet = true;
			// }
			// }
			// } catch (OutOfMemoryError e) {
			// }

		    }

		}

		if (mImageManagerHandler != null) {
		    Message message = mImageManagerHandler.obtainMessage(
			    MSG_REPLY, bitmap);
		    mImageManagerHandler.sendMessage(message);
		}
		break;

	    case MSG_STOP: // 收到终止指令
		Looper.myLooper().quit();
		break;

	    }
	}
    }

    /** UI线程消息处理�? */
    private Handler mImageManagerHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    if (msg != null) {
		switch (msg.what) {

		case MSG_REPLY: // 收到应答

		    do {
			ImageRef imageRef = mRequestQueue.remove();

			if (imageRef == null)
			    break;

			if (imageRef.imageView == null
				|| imageRef.imageView.getTag() == null
				|| imageRef.url == null)
			    break;

			if (!(msg.obj instanceof Bitmap) || msg.obj == null) {
			    break;
			}
			Bitmap bitmap = (Bitmap) msg.obj;

			// 非同�?ImageView
			if (!(imageRef.url).equals((String) imageRef.imageView
				.getTag())) {
			    break;
			}

			setImageBitmap(imageRef.imageView, bitmap, isFromNet);
			isFromNet = false;

		    } while (false);

		    break;
		}
	    }
	    // 设置闲置标志
	    mImageLoaderIdle = true;

	    // 若服务未关闭，则发�?�下�?个请求�??
	    if (mImageLoaderHandler != null) {
		sendRequest();
	    }
	}
    };

    /**
     * 添加图片显示渐现动画
     * 
     */
    private void setImageBitmap(ImageView imageView, Bitmap bitmap,
	    boolean isTran) {
	// if (isTran) {
	// final TransitionDrawable td = new TransitionDrawable(new Drawable[] {
	// new ColorDrawable(android.R.color.transparent), new
	// BitmapDrawable(bitmap) });
	// td.setCrossFadeEnabled(true);
	// imageView.setImageDrawable(td);
	// td.startTransition(10);
	// } else {
	imageView.setImageBitmap(bitmap);
	// }

    }

    // /**
    // * 从网络获取图片字节数�?
    // *
    // * @param url
    // * @return
    // */
    // private byte[] loadByteArrayFromNetwork(String url) {
    //
    // try {
    //
    // HttpGet method = new HttpGet(url);
    // HttpResponse response = myapp.getHttpClient().execute(method);
    // HttpEntity entity = response.getEntity();
    // return EntityUtils.toByteArray(entity);
    //
    // } catch (Exception e) {
    // return null;
    // }
    //
    // }

    /**
     * 根据url生成缓存文件完整路径�?
     * 
     * @param url
     * @return
     */
    public String urlToFilePath(String url) {

	// 扩展名位�?
	int index = url.lastIndexOf('.');
	if (index == -1) {
	    return null;
	}

	StringBuilder filePath = new StringBuilder();

	// 图片存取路径
	filePath.append(myapp.getCacheDir().toString()).append('/');

	// 图片文件�?
	filePath.append(MD5.Md5(url)).append(url.substring(index));

	return filePath.toString();
    }

    /**
     * Activity#onStop后，ListView不会有残余请求�??
     */
    public void stop() {

	// 清空请求队列
	mImageQueue.clear();

    }

}
