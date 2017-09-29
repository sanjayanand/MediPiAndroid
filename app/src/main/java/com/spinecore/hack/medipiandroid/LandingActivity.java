package com.spinecore.hack.medipiandroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.signove.health.service.HealthAgentAPI;
import com.signove.health.service.HealthService;
import com.signove.health.service.HealthServiceAPI;
import com.spinecore.hack.medipiandroid.store.CollectionPagerAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    BlankFragment.OnFragmentInteractionListener,
                    InstructionFragment.OnFragmentInteractionListener {

    int[] specs = {0x1004, 0x1007};
    Handler tm;
    HealthServiceAPI api;
    TextView status;
    TextView msg;
    TextView device;
    TextView data;
    Map<String, String> map;

    private String reading_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView userSummary = (TextView) toolbar.findViewById(R.id.toolbar_login_user_summary);
        String userDetails = getIntent().getExtras().getString("userDetails");
        userSummary.setText(userDetails);

        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.info);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Init bluetooth listener

        map = new HashMap<String, String>();
        tm = new Handler();
        Intent intent = new Intent(getApplicationContext(), HealthService.class);
        startService(intent);
        bindService(intent, serviceConnection, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());


        return true;
    }
    public void displayView(int viewId) {

        Fragment fragment = null;
        boolean instructions = false;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.oximeter:
                reading_type="oximeter";
                fragment = BlankFragment.newInstance(reading_type);
                title  = "Oximeter";
                instructions = true;
                break;
            case R.id.weight:
                reading_type="weight";
                fragment = BlankFragment.newInstance(reading_type);
                title  = "Weight";
                instructions = true;
                break;
            case R.id.bp:
                reading_type="bp";
                fragment = BlankFragment.newInstance(reading_type);
                title  = "Blood Pressure";
                instructions = true;
                break;
            case R.id.questionnaire:
                fragment = new QuestionnaireFragment();
                title  = "Questionnaire";
                break;
            case R.id.share_reading:
                fragment = new ReadingFragment();
                title  = "Share Reading";
                break;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CollectionPagerAdapter mCollectionPagerAdapter =
                    new CollectionPagerAdapter(
                            getSupportFragmentManager());
            transaction.replace(R.id.content_frame, fragment);
            if (instructions) {
                ViewPager mViewPager = (ViewPager) findViewById(R.id.instruction_pager);
                mViewPager.setAdapter(mCollectionPagerAdapter);
            }
            transaction.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void show(TextView field, String msg) {
        final TextView f = field;
        final String s = msg;
        tm.post(new Runnable() {
            public void run() {
                f.setText(s);
            }
        });
    }

    public Document parse_xml(String xml) {
        Document d = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            d = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (ParserConfigurationException e) {
            Log.w("Antidote", "XML parser error");
        } catch (SAXException e) {
            Log.w("Antidote", "SAX exception");
        } catch (IOException e) {
            Log.w("Antidote", "IO exception in xml parsing");
        }

        return d;
    }

    public void show_dev(String path) {
        if (map.containsKey(path)) {
            show(device, "Device " + map.get(path));
        } else {
            show(device, "Unknown device " + path);
        }
    }

    public void handle_packet_connected(String path, String dev) {
        map.put(path, dev);
        show_dev(path);
        show(msg, "Connected");
    }

    public void handle_packet_disconnected(String path) {
        show(msg, "Disconnected");
        show_dev(path);
    }

    public void handle_packet_associated(String path, String xml) {
        show(msg, "Associated");
        show_dev(path);
    }

    public void handle_packet_disassociated(String path) {
        show(msg, "Disassociated");
        show_dev(path);
    }

    public void handle_packet_description(String path, String xml) {
        show(msg, "MDS received");
        show_dev(path);
    }

    public String get_xml_text(Node n) {
        String s = null;
        NodeList text = n.getChildNodes();

        for (int l = 0; l < text.getLength(); ++l) {
            Node txt = text.item(l);
            if (txt.getNodeType() == Node.TEXT_NODE) {
                if (s == null) {
                    s = "";
                }
                s += txt.getNodeValue();
            }
        }

        return s;
    }


    public void handle_packet_measurement(String path, String xml) {

        data = (TextView) findViewById(R.id.measurement);
        msg = (TextView) findViewById(R.id.msg);
        device = (TextView) findViewById(R.id.device);
        status = (TextView) findViewById(R.id.status);

        String measurement = "";
        Document d = parse_xml(xml);

        if (d == null) {
            return;
        }

        NodeList datalists = d.getElementsByTagName("data-list");

        for (int i = 0; i < datalists.getLength(); ++i) {

            Log.w("Antidote", "processing datalist " + i);

            Node datalist_node = datalists.item(i);
            NodeList entries = ((org.w3c.dom.Element) datalist_node).getElementsByTagName("entry");

            for (int j = 0; j < entries.getLength(); ++j) {

                Log.w("Antidote", "processing entry " + j);

                boolean ok = false;
                String unit = "";
                String value = "";

                Node entry = entries.item(j);

                // scan immediate children to dodge entry inside another entry
                NodeList entry_children = entry.getChildNodes();

                for (int k = 0; k < entry_children.getLength(); ++k) {
                    Node entry_child = entry_children.item(k);

                    Log.w("Antidote", "processing entry child " + entry_child.getNodeName());

                    if (entry_child.getNodeName().equals("simple")) {
                        // simple -> value -> (text)
                        NodeList simple = ((org.w3c.dom.Element) entry_child).getElementsByTagName("value");
                        NodeList simpleName = ((org.w3c.dom.Element) entry_child).getElementsByTagName("name");
                        Log.w("Antidote", "simple.value count: " + simple.getLength());
                        if (simple.getLength() > 0) {
                            String text = get_xml_text(simple.item(0));
                            String name = get_xml_text(simpleName.item(0));
                            if (text != null && name != null && name.equals("Basic-Nu-Observed-Value")) {
                                ok = true;
                                value = text;
                            }
                        }
                    } else if (entry_child.getNodeName().equals("meta-data")) {
                        // meta-data -> meta name=unit
                        NodeList metas = ((org.w3c.dom.Element) entry_child).getElementsByTagName("meta");

                        Log.w("Antidote", "meta-data.meta count: " + metas.getLength());

                        for (int l = 0; l < metas.getLength(); ++l) {
                            Log.w("Antidote", "Processing meta " + l);
                            NamedNodeMap attr = metas.item(l).getAttributes();
                            if (attr == null) {
                                Log.w("Antidote", "Meta has no attributes");
                                continue;
                            }
                            Node item = attr.getNamedItem("name");
                            if (item == null) {
                                Log.w("Antidote", "Meta has no 'name' attribute");
                                continue;
                            }

                            Log.w("Antidote", "Meta attr 'name' is " + item.getNodeValue());

                            if (item.getNodeValue().equals("unit")) {
                                Log.w("Antidote", "Processing meta unit");
                                String text = get_xml_text(metas.item(l));
                                if (text != null) {
                                    unit = text;
                                }
                            }
                        }

                    }
                }

                if (ok) {
                    if (unit != "")
                        measurement += value + " " + unit + "\n";
                    else
                        measurement += value + " ";
                }
            }
        }

        show(data, measurement);
        show(msg, "Measurement");
        show_dev(path);
    }

    private void RequestConfig(String dev) {
        try {
            Log.w("HST", "Getting configuration ");
            String xmldata = api.GetConfiguration(dev);
            Log.w("HST", "Received configuration");
            Log.w("HST", ".." + xmldata);
        } catch (RemoteException e) {
            Log.w("HST", "Exception (RequestConfig)");
        }
    }

    private void RequestDeviceAttributes(String dev) {
        try {
            Log.w("HST", "Requested device attributes");
            api.RequestDeviceAttributes(dev);
        } catch (RemoteException e) {
            Log.w("HST", "Exception (RequestDeviceAttributes)");
        }
    }


    private HealthAgentAPI.Stub agent = new HealthAgentAPI.Stub() {
        @Override
        public void Connected(String dev, String addr) {
            Log.w("HST", "Connected " + dev);
            Log.w("HST", "..." + addr);
            data = (TextView) findViewById(R.id.measurement);
            msg = (TextView) findViewById(R.id.msg);
            device = (TextView) findViewById(R.id.device);
            status = (TextView) findViewById(R.id.status);
            handle_packet_connected(dev, addr);
        }

        @Override
        public void Associated(String dev, String xmldata) {
            final String idev = dev;
            Log.w("HST", "Associated " + dev);
            Log.w("HST", "...." + xmldata);
            data = (TextView) findViewById(R.id.measurement);
            msg = (TextView) findViewById(R.id.msg);
            device = (TextView) findViewById(R.id.device);
            status = (TextView) findViewById(R.id.status);
            handle_packet_associated(dev, xmldata);

            Runnable req1 = new Runnable() {
                public void run() {
                    RequestConfig(idev);
                }
            };
            Runnable req2 = new Runnable() {
                public void run() {
                    RequestDeviceAttributes(idev);
                }
            };
            tm.postDelayed(req1, 1);
            tm.postDelayed(req2, 500);
        }

        @Override
        public void MeasurementData(String dev, String xmldata) {
            Log.w("HST", "MeasurementData " + dev);
            Log.w("HST", "....." + xmldata);
            data = (TextView) findViewById(R.id.measurement);
            msg = (TextView) findViewById(R.id.msg);
            device = (TextView) findViewById(R.id.device);
            status = (TextView) findViewById(R.id.status);
            handle_packet_measurement(dev, xmldata);
        }

        @Override
        public void DeviceAttributes(String dev, String xmldata) {
            Log.w("HST", "DeviceAttributes " + dev);
            Log.w("HST", ".." + xmldata);
            data = (TextView) findViewById(R.id.measurement);
            msg = (TextView) findViewById(R.id.msg);
            device = (TextView) findViewById(R.id.device);
            status = (TextView) findViewById(R.id.status);
            handle_packet_description(dev, xmldata);
        }

        @Override
        public void Disassociated(String dev) {
            Log.w("HST", "Disassociated " + dev);
            data = (TextView) findViewById(R.id.measurement);
            msg = (TextView) findViewById(R.id.msg);
            device = (TextView) findViewById(R.id.device);
            status = (TextView) findViewById(R.id.status);
            handle_packet_disassociated(dev);
        }

        @Override
        public void Disconnected(String dev) {
            Log.w("HST", "Disconnected " + dev);
            data = (TextView) findViewById(R.id.measurement);
            msg = (TextView) findViewById(R.id.msg);
            device = (TextView) findViewById(R.id.device);
            status = (TextView) findViewById(R.id.status);
            handle_packet_disconnected(dev);
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w("HST", "Service connection established");

            // that's how we get the client side of the IPC connection
            api = HealthServiceAPI.Stub.asInterface(service);
            try {
                Log.w("HST", "Configuring...");
                api.ConfigurePassive(agent, specs);
            } catch (RemoteException e) {
                Log.e("HST", "Failed to add listener", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w("HST", "Service connection closed");
        }

    };

    private class CollectionPagerAdapter extends FragmentStatePagerAdapter {

        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            InstructionFragment fragment = InstructionFragment.newInstance();
            Bundle args = new Bundle();
            args.putString(InstructionFragment.ARG_READING_TYPE, reading_type);
            args.putInt(InstructionFragment.ARG_INSTRUCTION_STEP, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            if (reading_type == "oximeter") {
                return getResources().getStringArray(R.array.oximeter_instructions).length;
            } else if (reading_type == "weight") {
                return getResources().getStringArray(R.array.weight_instructions).length;
            } else if (reading_type == "bp") {
                return getResources().getStringArray(R.array.bp_instructions).length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }

        // top
    }
}

