package com.fjbg.thebeercode;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.fjbg.thebeercode.MesVotes.GetMoarVotes;
import com.fjbg.thebeercode.MesVotes.GetVotes;
import com.fjbg.thebeercode.model.BiereDB;
import com.fjbg.thebeercode.model.VueVoteDB;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AffichageBiere extends Activity {
	
	public static final String SELECTEDBEER = "BEER";
	
	private ListView listComments;
	private TextView BeerName;
	private TextView BeerCountry;
	private TextView ABV;
	private TextView NbVotes;
	private ImageView BeerPicture;
	private RatingBar ratingBeer;
	private Button retour = null;
	ProgressDialog progress;
	Boolean scroll = true;

	ArrayList<VueVoteDB> listVotes;
	int items;
	VotesBiereAdapter vbA;
	private BiereDB biere;
	private String nomBiere;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_affichage_biere);
		
		BeerName = (TextView)findViewById(R.id.Beername);
		BeerCountry = (TextView)findViewById(R.id.BeerCountry);
		ABV = (TextView)findViewById(R.id.ABV);
		NbVotes = (TextView)findViewById(R.id.NbVotes);
		
		BeerPicture = (ImageView) findViewById(R.id.BeerPicture);
		
		ratingBeer = (RatingBar) findViewById(R.id.ratingBeer);
		
		listComments = (ListView)findViewById(R.id.listComments);
		
		retour = (Button) findViewById(R.id.btBack);
		retour.setOnClickListener(retourListener);
		
		Intent i=getIntent();
	    nomBiere=(String)i.getStringExtra(SELECTEDBEER);
	    
	    Lecture lec= new Lecture();
	    lec.execute();
	    
	    listVotes = new ArrayList<VueVoteDB>();
	    vbA = new VotesBiereAdapter(AffichageBiere.this, listVotes);
		listComments.setAdapter(vbA);
		GetVotes getter = new GetVotes();
		getter.execute();
	}
	
	private OnClickListener retourListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
	public void loadMore(int offset) {
		if(scroll) {
		GetMoarVotes getMore = new GetMoarVotes();
		getMore.execute();
		}
	}
	
	private void addItems(VueVoteDB item) {
		if (item != null){
			this.vbA.add(item);
			this.vbA.notifyDataSetChanged();
			items++;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.affichage_biere, menu);
		return true;
	}
	
	public class Lecture extends AsyncTask<String, Integer, Boolean>{	
		Boolean exc = false;
		Exception ex;
		BiereDB biereRech;
		Bitmap bitmap   = null;
		File downloadFile;
		File file;
		
		public Lecture() {

		}
		
		@Override
        protected void onPreExecute(){
                progress = new ProgressDialog(AffichageBiere.this);
                progress.setMessage("T�l�chargement des donn�es en cours...");
                progress.setCancelable(false);
                progress.show();
        }

		@Override
		protected Boolean doInBackground(String... arg0) {
			
			biereRech =new BiereDB();
			
			biereRech.setNomBiere(nomBiere);
			
			try{
				biereRech.readBiere();
			}
			catch(Exception e){
				ex = e;
				exc = true;
			}
			
			
			if(!exc){
//				FTPClient ftp = null;
//				try {
//				FTPClient mFTPClient = new FTPClient();
//				  mFTPClient.connect("ftp.alokar.site90.net",21);      
//				  mFTPClient.login("a7115779", "projet2013");
//				  mFTPClient.enterLocalPassiveMode();
//				InputStream inputStream = mFTPClient.retrieveFileStream(biereRech.getCheminImage());
//                file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.png"); // TODO Changer le nom du fichier + supprimer apr�s lecture
//                file.createNewFile();
//                OutputStream outputStream = new FileOutputStream(file);
//                int read = 0;
//                byte[] bytes = new byte[1024*1024];
//                while ((read = inputStream.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, read);
//                }
//                outputStream.close();
//                inputStream.close();
//				}catch(Exception e) {
//					Log.d("exception", "exception : " + e.getMessage());
//				}
				
			
				
//					ftp = new FTPClient();
//					ftp.connect("ftp.alokar.site90.net",21);
//					ftp.login("a7115779", "projet2013");
//					ftp.setFileType(FTP.BINARY_FILE_TYPE);
//					ftp.enterLocalPassiveMode();
//					Log.d("FTP", "1");
//					
//					FTPClient mFTPClient = new FTPClient();
//					  mFTPClient.connect("ftp.alokar.site90.net",21);      
//					  mFTPClient.login("a7115779", "projet2013");
//					  mFTPClient.enterLocalPassiveMode();
//					  InputStream inStream = mFTPClient.retrieveFileStream(biereRech.getCheminImage());
//					  InputStreamReader isr = new InputStreamReader(inStream, nomBiere);

//					String remoteFile = biereRech.getCheminImage();
//					String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//					File downloadFile = new File(path + "/tmp.jpg");
//					OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
//					Boolean success = false;
//					try {
//						success = ftp.retrieveFile(remoteFile, outputStream);
//					} catch(Exception e) {
//						Log.d("succes", "succes : " + e.getMessage());
//					}
//					outputStream.flush();
//					outputStream.close();
//					if(success) {
//						Log.d("success", "success");
////						BitmapFactory.Options options = new BitmapFactory.Options();
////						bitmap = BitmapFactory.decodeFile(downloadFile.getAbsolutePath(), options);
//						
//					}
//					FTPClient client = new FTPClient();
//			        FileOutputStream fos = null;
//			        String remoteFile = biereRech.getCheminImage();
//			 
//			        try {
//			            client.connect("ftp.alokar.site90.net", 21);
//			            client.login("a7115779", "projet2013");
//			            
//			            //
//			            // The remote filename to be downloaded.
//			            //
//			            String filename = "image_Noir.jpg";
//			            fos = new FileOutputStream(filename);
//			 
//			            //
//			            // Download file from FTP server
//			            //
//			            client.retrieveFile("/" + filename, fos);
//			        } catch (IOException e) {
//			            Log.d("Exception", "exception : " + e.getMessage());
//			        } finally {
//			            try {
//			                if (fos != null) {
//			                    fos.close();
//			                }
//			                client.disconnect();
//			            } catch (IOException e) {
//			                e.printStackTrace();
//			            }
//			        }
//		       
//				 }
//				 catch(Exception e){
//					 Log.d("Exception", "exception : " + e.getMessage());
//					 
//				 }
//				 finally {
//					 try{
//						 if(ftp != null){
//							 ftp.logout();
//							 ftp.disconnect();
//						 }
//					 }
//					 catch(Exception e){
//					 }
//				  }
					
					
				}
			
			return true;
		}
		
		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			try {
				bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
			}catch (Exception e) {
				Log.d("exception post", "exception 2 : " + e.getMessage());  // TODO reception d'exception quand affichage via MesFavoris
			}				
			BeerPicture.setImageBitmap(bitmap);
			if(progress.isShowing())
				progress.dismiss();
			if(exc) {
				Toast.makeText(AffichageBiere.this, ex.getMessage(), Toast.LENGTH_SHORT ).show();
			}
			else {
				try{
					biere=biereRech;
					BeerName.setText(biere.getNomBiere());
					BeerCountry.setText(biere.getPaysBiere());
					ABV.setText(String.valueOf(biere.getDegreBiere()));
					ratingBeer.setRating((biere.getCoteBiere())/2);
					NbVotes.setText(String.valueOf(biere.getNbreVotes()));
				}
				catch(Exception e){
					Toast.makeText(AffichageBiere.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
				}
			}
			
		}
	}
	
	public class GetVotes extends AsyncTask<String, Integer, Boolean>{
		Boolean exc = false;
		Exception ex;
		ArrayList<VueVoteDB> liste = new ArrayList<VueVoteDB>();

		public GetVotes() {

		}
		
		@Override
        protected void onPreExecute(){
                
        }

		@Override
		protected Boolean doInBackground(String... arg0) {
			try {				
				liste = VueVoteDB.readVotesBiere(biere.getIdBiere(), 1, 10);

				listComments.setOnScrollListener(new EndlessScrollListener(5) {
					@Override
					public void onLoadMore(int page, int totalItemsCount) {
						loadMore(totalItemsCount);
					}
				});
			}catch(Exception e) {
				ex = e;
				exc = true;
				scroll = false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
			for(VueVoteDB vote : liste) {
				addItems(vote);
			}
			if(exc) {
				Toast.makeText(AffichageBiere.this, ex.getMessage(), Toast.LENGTH_SHORT ).show();
			}			
		}
	}

	public class GetMoarVotes extends AsyncTask<String, Integer, Boolean>{
		Boolean exc = false;
		Exception ex;
		ArrayList<VueVoteDB> liste;

		public GetMoarVotes() {

		}
		
		@Override
        protected void onPreExecute(){
             liste = new ArrayList<VueVoteDB>(); 
        }

		@Override
		protected Boolean doInBackground(String... arg0) {
			try {				
				liste = VueVoteDB.readVotesBiere(biere.getIdBiere(), items+1, items+6);			
			}catch(Exception e) {
				ex = e;
				exc = true;
				scroll = false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
			for(VueVoteDB vote : liste) {
				addItems(vote);
			}
			if(exc) {
				Toast.makeText(AffichageBiere.this, ex.getMessage(), Toast.LENGTH_SHORT ).show();
			}
		}
	}

}