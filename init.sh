mkdir /ocr
mkdir /ocr/storage

# copy tessdata
cp -r tessdata /ocr

# init db
mysql -u root -p < database/db.sql