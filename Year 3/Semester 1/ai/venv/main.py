import SimpleITK as sitk
import numpy as np

# A path to a T1-weighted brain .nii image:
t1_fn = 'D:\\Users\\DenisGabrielVieriu\\Downloads\\Ground_truth\\training_axial_full_pat1-label.nii'

# Read the .nii image containing the volume with SimpleITK:
sitk_t1 = sitk.ReadImage(t1_fn)

# and access the numpy array:
t1 = sitk.GetArrayFromImage(sitk_t1)

print(t1)