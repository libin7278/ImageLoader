package imageloader.libin.com.imageloaderdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import imageloader.libin.com.imageloaderdemo.R;


public class ViewpagerActivity extends Activity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<String> urls = new ArrayList<>();
       /* urls.add("/storage/emulated/0/DCIM/家里有用图/IMG_20170222_221249_HHT.jpg");
        urls.add("/storage/emulated/0/DCIM/家里有用图/IMG_20161114_231649.jpg");
        urls.add("/storage/emulated/0/DCIM/家里有用图/IMG_20161229_221023.jpg");
        urls.add("/storage/emulated/0/DCIM/家里有用图/DSC_0051.JPG");*/
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359324536&di=f1c2dfd6b0ebe0043f089d933d5d9e10&imgtype=0&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2Ffd%2Ftg%2Fg1%2FM02%2FFE%2FB5%2FCghzfFSrqqCATzfcACG0aD33PsY070.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359343773&di=9fc5478b63f369090613c1c27d56f237&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201510%2F04%2F20151004210446_usmze.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359365740&di=f02eadaa956bef73d64cf9fb86969228&imgtype=0&src=http%3A%2F%2Fupload4.hlgnet.com%2Fbbsupfile%2F2012%2F2012-07-17%2F20120717003503_80.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359415501&di=2457a1060f3ccde0bd7f7b5d0891918d&imgtype=0&src=http%3A%2F%2Fdimg09.c-ctrip.com%2Fimages%2Ffd%2Ftg%2Fg2%2FM03%2FCB%2F19%2FCghzf1UualCAdMmaABQVh70n7_g185.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359451818&di=b3031652757061d8d9a681e824c8a9e5&imgtype=0&src=http%3A%2F%2Flvyou.panjin.net%2Ffjpic%2F1299485962.jpg");

        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490954196&di=9d3311b40886fc2c670a31f7ba1edb68&imgtype=jpg&er=1&src=http%3A%2F%2Fdimg05.c-ctrip.com%2Fimages%2Ffd%2Ftg%2Fg2%2FM0B%2FCB%2F14%2FCghzgFUuapGABMHyACKrkXeB1zo976.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359527096&di=ec923d22df64850456fa01de50540ed3&imgtype=0&src=http%3A%2F%2Ffile28.mafengwo.net%2FM00%2F38%2FEB%2FwKgB6lTHUrKAMiRNABF8ZlX_qGY71.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490954377&di=c037874ec1de4ad2528708b89123ffb9&imgtype=jpg&er=1&src=http%3A%2F%2Ffile27.mafengwo.net%2FM00%2F44%2FFA%2FwKgB6lTHYfuAGx8tAAyTEuzr7rQ63.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359641404&di=f0e34cfcfd8789ad6fdddf28ad7dc49b&imgtype=0&src=http%3A%2F%2Ffile27.mafengwo.net%2FM00%2F23%2FFC%2FwKgB6lSBlHCAa62UAAt4XAl0sUc50.jpeg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490359621573&di=fe03f67cb5b06f8961ceb8e21ca07db7&imgtype=0&src=http%3A%2F%2Ffile21.mafengwo.net%2FM00%2F7B%2F55%2FwKgB3FDF162Afrj8ACtU8-OAkZ484.jpeg");

        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490971670&di=d810318cdd61531a5d1d3861d00c2d9e&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fc%2F570cc782c8312.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377045881&di=0f13095a141036c21225c377dd7749a9&imgtype=0&src=http%3A%2F%2Ftpic.home.news.cn%2FxhCloudNewsPic%2Fxhpic1501%2FM09%2F3B%2F9F%2FwKhTlFjGSD-EBUVnAAAAADRqVbw103.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377064336&di=887d8cb3473ac568fca9e4e7914039f7&imgtype=0&src=http%3A%2F%2Ftpic.home.news.cn%2FxhCloudNewsPic%2Fxhpic1501%2FM02%2F3B%2F36%2FwKhTlFi8xGiESf0ZAAAAAKAbE3k190.JPG");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377078620&di=e151b087ab39e1a630a4b233d2ae1b3c&imgtype=0&src=http%3A%2F%2Ftpic.home.news.cn%2FxhCloudNewsPic%2Fxhpic1501%2FM0B%2F3A%2FEA%2FwKhTlFi2HTqEQqC7AAAAABGsE-A602.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377147917&di=ebd07f2123fd27bfd6ab721ef5bc10be&imgtype=0&src=http%3A%2F%2Ftpic.home.news.cn%2FxhCloudNewsPic%2Fxhpic1501%2FM07%2F3A%2FEA%2FwKhTlFi2HTmEd_tPAAAAAAAmh48401.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377163830&di=3b35cedcc3fe72fa6d446de5f658e37a&imgtype=0&src=http%3A%2F%2Ftpic.home.news.cn%2FxhCloudNewsPic%2Fxhpic1501%2FM04%2F24%2FF8%2FwKhTlVfWBNaEfokmAAAAAIa_flU883.JPG");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377184956&di=358135e6e95914ac85483fb127f2f61c&imgtype=0&src=http%3A%2F%2Fbbs11.djicdn.com%2Fdata%2Fattachment%2Fforum%2F201605%2F11%2F200721bvbrtifzgczc7xqc.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490971909&di=4e376f64ea228a1360759339b183bb0f&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.wulong9.com%2Fuploads%2Fallimg%2F160901%2F2339441392-8.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377224967&di=7916a9c76cfd76540b3ed41f970c8b05&imgtype=0&src=http%3A%2F%2Fbbs11.djicdn.com%2Fdata%2Fattachment%2Fforum%2F201511%2F22%2F200600doo6tz2zqx6jdz5d.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377243693&di=5471e90a84737494794986fe81833f9f&imgtype=0&src=http%3A%2F%2Fnews.cpd.com.cn%2Fn203193%2Fc29879991%2Fpart%2F29880124.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377264171&di=6e8aa952f387588e9dce20ce6da70b90&imgtype=0&src=http%3A%2F%2Fimg2.xiangshu.com%2FDay_130214%2F102_721302_a48bffc61d51fcf.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377296064&di=88b89e2b139e540e321dfeac8b1e45c5&imgtype=0&src=http%3A%2F%2Fwww.liuzhou.gov.cn%2Fxwzx%2Fqxdt%2Frax%2F201607%2FW020160729407716095395.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377327685&di=b2630926b28c9eb55845fb070cdd873a&imgtype=0&src=http%3A%2F%2Fimg.meyet.com%2Fforum%2F201509%2F13%2F161505r4lp1l9x93hsrxj9.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377349229&di=e756188f5130990ddcf2026b4c0653f2&imgtype=0&src=http%3A%2F%2Fimg.zhongguowangshi.com%2FxwSceneReport%2F201408%2F20140806201621_2568.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377365522&di=03b5ca67a2b928a81755c71d3234960b&imgtype=0&src=http%3A%2F%2Fpicture.youth.cn%2Fqtdb%2F201604%2FW020160401504554381405.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377388544&di=23710a64289c9e642a0938a208f5c001&imgtype=0&src=http%3A%2F%2Fnews.cpd.com.cn%2Fn203193%2Fc30062120%2Fpart%2F30062148.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377446523&di=881526f14e32deac9bf6135b0fcd9607&imgtype=0&src=http%3A%2F%2Fimg.xiangshu.com%2FDay_160923%2F170_626871_adcb95228d97079.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490972194&di=e447e1da8606c77991bbad5a5e31259f&imgtype=jpg&er=1&src=http%3A%2F%2Fww1.sinaimg.cn%2Flarge%2F005CX4pyjw1erxh54tsawj32531lr4qp.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490972241&di=eff2b007af75130d6646646f56332bf2&imgtype=jpg&er=1&src=http%3A%2F%2Ftpic.home.news.cn%2FxhCloudNewsPic%2Fxhpic1501%2FM02%2F3B%2F36%2FwKhTlFi8xGiESf0ZAAAAAKAbE3k190.JPG");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377550943&di=44e2f1358062eb16e7b5922e8abe0823&imgtype=0&src=http%3A%2F%2Fimage97.360doc.com%2FDownloadImg%2F2016%2F06%2F1500%2F73937627_2.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490377590254&di=a794edea83b41bfc8f116b4568c89194&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F6e4b60cegw1f1td78f79aj23341qiu0y.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490972384&di=3cafec5973c15688726cec59e1d9085e&imgtype=jpg&er=1&src=http%3A%2F%2Fbbs11.djicdn.com%2Fdata%2Fattachment%2Fforum%2F201604%2F01%2F133253wg01kxggh8o215gc.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490972455&di=53778e44f17970656d0dc2e0a9ce4ee9&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.7695556.com%2Fdata%2Fattachment%2Fforum%2F201609%2F28%2F201212dpyqwo0aam49owpa.jpg");

        // PagerAdapterForBigImage adapter = new PagerAdapterForBigImage(urls);
        // viewPager.setAdapter(adapter);
        //ImageLoader.loadBigImages(viewPager,urls);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //viewPager.destroyDrawingCache();
//        ImageLoader.clearAllMemoryCaches();//调了没用,也不需要调,下次进来自动会刷新内存
    }
}
