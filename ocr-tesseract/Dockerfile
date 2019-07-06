FROM tesseractshadow/tesseract4cmp

# Update repos
RUN /home/scripts/repos_update.sh

# Build leptonica
RUN /home/scripts/compile_leptonica.sh

# Build tesseract
RUN /home/scripts/compile_tesseract.sh

# Build packages
RUN /home/scripts/build_deb_pkg.sh