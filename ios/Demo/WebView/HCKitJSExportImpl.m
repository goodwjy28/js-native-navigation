//
//  BotsKitJSExportImpl.m
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/1.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCKitJSExportImpl.h"

@interface HCKitJSExportImpl ()<UIImagePickerControllerDelegate, UINavigationControllerDelegate>

@property (nonatomic, weak) HCJSCoreBaseViewController *vcContext;

@property (nonatomic, strong) JSValue *imageValue;

@end
@implementation HCKitJSExportImpl

+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext {
    HCKitJSExportImpl *impl = [HCKitJSExportImpl new];
    impl.vcContext = vcContext;
    return impl;
}

- (void)cameraWithResult:(JSValue *)result {
    self.vcContext.jsThread = [NSThread currentThread];
    _imageValue = result;
    dispatch_async(dispatch_get_main_queue(), ^{
        UIImagePickerController * imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.editing = YES;
        imagePicker.delegate = self;
        imagePicker.allowsEditing = YES;
        
        UIAlertController * alert = [UIAlertController alertControllerWithTitle:@"请选择打开方式" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
        
        UIAlertAction * camera = [UIAlertAction actionWithTitle:@"相机" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            imagePicker.sourceType =  UIImagePickerControllerSourceTypeCamera;
            imagePicker.modalPresentationStyle = UIModalPresentationFullScreen;
            imagePicker.cameraCaptureMode = UIImagePickerControllerCameraCaptureModePhoto;
            [self.vcContext presentViewController:imagePicker animated:YES completion:nil];
        }];
        
        UIAlertAction * photo = [UIAlertAction actionWithTitle:@"相册" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
            [self.vcContext presentViewController:imagePicker animated:YES completion:nil];
        }];
        
        UIAlertAction * cancel = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
            [self.vcContext dismissViewControllerAnimated:YES completion:nil];
        }];
        
        [alert addAction:camera];
        [alert addAction:photo];
        [alert addAction:cancel];
        
        [self.vcContext presentViewController:alert animated:YES completion:nil];
    });
}

#pragma mark - imagePickerController delegate

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> *)info {
    
    [picker dismissViewControllerAnimated:YES completion:nil];
    UIImage * image = [info valueForKey:UIImagePickerControllerEditedImage];
    NSData *imageData = UIImagePNGRepresentation(image);
    NSString *imageBase64 = [imageData base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
    NSDictionary *dict = @{@"image": imageBase64};
    if (self.imageValue) {
        [self.vcContext executeJSValueThreadSafe:self.imageValue args:@[dict]];
    }
}

@end
