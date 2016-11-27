/**
 * 
 */
package adapter;

import java.io.File;
import java.util.HashMap;

import java.util.ArrayList;

import tools.Config;

import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class StyleSelectListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    private DisplayImageOptions options;

    public StyleSelectListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
	this.context = context;
	this.lInflater = LayoutInflater.from(context);
	preperImageLoader();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
	// TODO Auto-generated method stub
	return list.get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	ViewHolder vHolder = new ViewHolder();
	if (convertView == null) {
	    convertView = lInflater.inflate(R.layout.style_select_list_item,
		    null);
	}
	vHolder.style = (TextView) convertView
		.findViewById(R.id.style_select_item_style);
	vHolder.count = (TextView) convertView
		.findViewById(R.id.style_select_item_count);
	vHolder.fit = (TextView) convertView
		.findViewById(R.id.style_select_item_fit);
	vHolder.intro = (TextView) convertView
		.findViewById(R.id.style_select_item_intro);
	vHolder.iv = (ImageView) convertView
		.findViewById(R.id.style_select_item_iv);
	
	vHolder.style.setText(list.get(position).get("classname"));
	
	vHolder.fit.setText(list.get(position).get("tjch"));
	vHolder.intro.setText(list.get(position).get("contents"));
	vHolder.count.setText("已有" + list.get(position).get("dds") + "人使用");
	ImageLoader.getInstance().displayImage(Config.URL + list.get(position).get("bgpic"), vHolder.iv, options);

	return convertView;
    }

    class ViewHolder {
	TextView style;
	TextView count;
	TextView fit;
	TextView intro;
	ImageView iv;
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
		.showImageForEmptyUri(R.drawable.style_bg)
		.showImageOnFail(R.drawable.style_bg).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }
}
