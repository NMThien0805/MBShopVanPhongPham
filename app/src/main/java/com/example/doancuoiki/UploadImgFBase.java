package com.example.doancuoiki;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.doancuoiki.api.ApiService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImgFBase {
    String IdObject;

    Uri mImageUri;

    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    StorageTask mUploadTask;

    ContentResolver cR;

    Context context;

    String url;

    MainActivity main = null;

    SanPham sanpham = null;

    Users user = null;

    //ins_or_upd = 1(insert) OR ins_or_upd = 2(update)
    int ins_or_upd = 0;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void set_for_user(Users user, Uri mImageUri, ContentResolver cR, Context context, int ins_or_upd) {
        IdObject = user.getMail();
        this.user = user;
        this.mImageUri = mImageUri;
        this.cR = cR;
        this.context = context;
        this.ins_or_upd = ins_or_upd;

        createObject();
    }

    public void set_for_product(SanPham sanpham, Uri mImageUri, ContentResolver cR, Context context, int ins_or_upd) {
        this.IdObject = sanpham.getTenSP();
        this.sanpham = sanpham;
        this.mImageUri = mImageUri;
        this.cR = cR;
        this.context = context;
        this.ins_or_upd = ins_or_upd;

        createObject();
    }

    public UploadImgFBase() {
    }

    public UploadImgFBase(String idObject, Uri mImageUri, ContentResolver cR, Context context, MainActivity main) {
        IdObject = idObject;
        this.mImageUri = mImageUri;
        this.cR = cR;
        this.context = context;
        this.main = main;

        createObject();
    }

    private void createObject() {
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
    }

    @Override
    public String toString() {
        return "UploadImgFBase{" +
                "IdObject='" + IdObject + '\'' +
                ", mImageUri=" + mImageUri +
                ", mStorageRef=" + mStorageRef +
                ", mDatabaseRef=" + mDatabaseRef +
                ", cR=" + cR +
                ", context=" + context +
                '}';
    }

    public StorageTask getmUploadTask() {
        return mUploadTask;
    }

    public String getIdObject() {
        return IdObject;
    }

    public void setIdObject(String idObject) {
        IdObject = idObject;
    }

    class ObjectItem{
        String IdBroject;
        String imgURL;

        @Override
        public String toString() {
            return "item{" +
                    "IdBroject='" + IdBroject + '\'' +
                    ", imgURL='" + imgURL + '\'' +
                    '}';
        }

        public void ObjectItem1(String idBroject, String imgURL) {
            IdBroject = idBroject;
            this.imgURL = imgURL;
        }

        public String getIdBroject() {
            return IdBroject;
        }

        public void setIdBroject(String idBroject) {
            IdBroject = idBroject;
        }

        public String getImgURL() {
            return imgURL;
        }

        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        public ObjectItem() {
        }
    }

    public void actionUpload(){
        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Successful");
                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    ObjectItem item = new ObjectItem();
                    item.ObjectItem1(IdObject, taskSnapshot.getUploadSessionUri().toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(item);

                    Uri uri = taskSnapshot.getDownloadUrl();

                    setUrl(uri.toString());

                    System.out.println("Result: " + uri.toString());

                    if(ins_or_upd == 1){
                        if(user != null){
                            insertUser(user, url);
                        }
                        else if(sanpham != null){
                            insertProduct(sanpham, url);
                        }
                    }
                    else if(ins_or_upd == 2){
                        if(user != null){
                            updateUser(user, url);
                        }
                        else if(sanpham != null){
                            updateProduct(sanpham, url);
                        }
                    }
                    else{
//                        Picasso.get().load(url).into(main.imgViewRes);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Fail: " + e.toString());
//                    Toast.makeText(context, "Failure_" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    System.out.println("progress: " + progress);
//                    Toast.makeText(context, String.valueOf(progress), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateProduct(SanPham sanpham, String url) {
        sanpham.setImg(url);
        Call<SanPham> res = ApiService.apiService.putEditPro(sanpham);
        res.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                Toast.makeText(getContext(), "RESULT: " + response.body(), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());
                //finish();
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                System.out.println("lỗi");
            }
        });
    }

    private void insertProduct(SanPham sanpham, String url) {
        sanpham.setImg(url);
        Call<SanPham> res = ApiService.apiService.insertProduct(sanpham);
        res.enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                Toast.makeText(getContext(), "RESULT: " + response.body(), Toast.LENGTH_SHORT).show();
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Toast.makeText(getContext(), "Call API Regiter Error", Toast.LENGTH_SHORT).show();
                Log.d("false", "sai ");
            }
        });
    }

    private void updateUser(Users user, String url) {
        user.setImg(url);
        Call<Users> res = ApiService.apiService.putEdit(user);
        res.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                System.out.println("ok");
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.out.println("lỗi");
            }
        });
    }

    private void insertUser(Users user, String url) {
        user.setImg(url);
//        System.out.println(user.toString());
        Call<Users> res = ApiService.apiService.register(user);
        res.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "RESULT: " + response.body(), Toast.LENGTH_SHORT).show();
                System.out.println("thành công");
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getContext(), "Call API Regiter Error", Toast.LENGTH_SHORT).show();
                Log.d("false", "sai ");
            }
        });
    }

    void actionShowUpload(){
    }

    private String getFileExtension(Uri uri){
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
