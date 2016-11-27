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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class DesignerListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    private DisplayImageOptions options;

    public DesignerListAdapter(ArrayList<HashMap<String, String>> list,
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
	    convertView = lInflater.inflate(R.layout.designer_list_item, null);
	}
	vHolder.name = (TextView) convertView
		.findViewById(R.id.designer_list_item_name);
	vHolder.sex = (ImageView) convertView
		.findViewById(R.id.designer_list_item_sex);
	vHolder.price = (TextView) convertView
		.findViewById(R.id.designer_list_item_price);
	vHolder.pic = (ImageView) convertView
		.findViewById(R.id.designer_list_item_pic);
	vHolder.level = (TextView) convertView
		.findViewById(R.id.designer_list_item_level);
	vHolder.count = (TextView) convertView
		.findViewById(R.id.designer_list_item_count);
	vHolder.rb = (RatingBar) convertView
		.findViewById(R.id.designer_list_item_ratingbar);
	vHolder.rate = (TextView) convertView
		.findViewById(R.id.designer_list_item_rate);
	vHolder.xsrl = (RelativeLayout) convertView
		.findViewById(R.id.designer_list_item_xsprice_ll);
	vHolder.xsPrice = (TextView) convertView
		.findViewById(R.id.designer_list_item_xsprice);
	vHolder.xshd = (ImageView) convertView
		.findViewById(R.id.designer_list_item_xshd);

	

	vHolder.name.setText(list.get(position).get("xm"));
	
	if(list.get(position).get("huodongdj").equals("0")){
	    vHolder.xshd.setVisibility(View.GONE);
	}else{
	    vHolder.xshd.setVisibility(View.VISIBLE);
	}
	
	if (list.get(position).get("xb").equals("1")) {
	    vHolder.sex.setImageResource(R.drawable.sex_male);
	} else {
	    vHolder.sex.setImageResource(R.drawable.sex_female);
	}
	if (list.get(position).get("je").equals("")) {
	    vHolder.price.setVisibility(View.INVISIBLE);
	    vHolder.xsrl.setVisibility(View.INVISIBLE);
	} else {
	    vHolder.price.setText("￥" + list.get(position).get("je"));
	    if(list.get(position).get("huodongdj").equals("0")){
		vHolder.xsrl.setVisibility(View.INVISIBLE);
	    }else{
		vHolder.xsrl.setVisibility(View.VISIBLE);
		vHolder.xsPrice.setText("￥" + list.get(position).get("huodongje"));
	    }
	}
	if (list.get(position).get("pic").startsWith("http")) {
	    ImageLoader.getInstance().displayImage(
		    list.get(position).get("pic"), vHolder.pic, options);
	} else {
	    ImageLoader.getInstance().displayImage(
		    Config.URL + "/" + list.get(position).get("pic"),
		    vHolder.pic, options);
	}

	vHolder.level.setText(list.get(position).get("dj"));
	float rate = Float.valueOf(list.get(position).get("zypj")) / 20;
	vHolder.rb.setRating(rate);
	vHolder.count.setText(" "+list.get(position).get("ds")+" ");
	vHolder.rate.setText(list.get(position).get("fwpj"));

	return convertView;
    }

    class ViewHolder {
	TextView name;
	ImageView sex;
	TextView price;
	ImageView pic;
	TextView level;
	RatingBar rb;
	TextView count;
	TextView rate;
	
	RelativeLayout xsrl;
	TextView xsPrice;
	ImageView xshd;
	
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
		.showImageForEmptyUri(R.drawable.question)
		.showImageOnFail(R.drawable.question).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }
}
