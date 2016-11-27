/**
 * 
 */
package adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import tools.Config;

import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class ADViewPagerAdapter extends PagerAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private DisplayImageOptions options;

    
    
    public ADViewPagerAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
	this.context = context;
	preperImageLoader();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size() == 0 ? 1 : list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
     * java.lang.Object)
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
	// TODO Auto-generated method stub
	return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
	View view = View.inflate(context, R.layout.adapter_ad, null);
	ImageView iv = (ImageView) view.findViewById(R.id.image);
	
	ImageLoader.getInstance().displayImage(Config.URL + list.get(position).get("pic"), iv, options);

	container.addView(view);// 一定不能少，将view加入到viewPager中
	
	return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
	// super.destroyItem(container, position, object);
	container.removeView((View) object);
    }
    
    
    private void preperImageLoader() {

	/******************* 配置ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		context).denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
		.build();// 开始构建

	options = new DisplayImageOptions.Builder().cacheInMemory()
		.cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.showImageForEmptyUri(R.drawable.htp)
		.showImageOnFail(R.drawable.htp).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }

}
