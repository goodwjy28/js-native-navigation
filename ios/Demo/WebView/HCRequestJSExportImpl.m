//
//  BotsRequestJSExportImpl.m
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/2.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCRequestJSExportImpl.h"
@interface HCRequestJSExportImpl ()

@property (nonatomic, weak) HCJSCoreBaseViewController *vcContext;

@property (nonatomic, strong) JSValue *success;
@property (nonatomic, strong) JSValue *fail;

@end
@implementation HCRequestJSExportImpl
+ (instancetype)instance:(HCJSCoreBaseViewController *)vcContext {
    HCRequestJSExportImpl *impl = [HCRequestJSExportImpl new];
    impl.vcContext = vcContext;
    return impl;
}

- (void)requestGet:(NSString *)url
            params:(NSDictionary *)params
           success:(JSValue *)success
              fail:(JSValue *)fail {
    self.vcContext.jsThread = [NSThread currentThread];
    self.success = success;
    self.fail = fail;
    NSMutableString *parameterString = [[NSMutableString alloc] initWithString:url];
    [parameterString appendString:@"?"];
    int pos =0;
    for (NSString *key in params.allKeys) {
        [parameterString appendFormat:@"%@=%@", key, params[key]];
        if(pos  < params.allKeys.count - 1){
            [parameterString appendString:@"&"];
        }
        pos++;
    }
    NSString * encodingString = [[parameterString copy] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    NSURL *_url = [NSURL URLWithString:encodingString];
    NSMutableURLRequest *urlRequest = [NSMutableURLRequest requestWithURL:_url];
    [urlRequest setTimeoutInterval:15.0];
    [urlRequest setHTTPMethod:@"GET"];
    [urlRequest setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSURLSessionConfiguration *sessionConfiguration = [NSURLSessionConfiguration defaultSessionConfiguration];
    NSURLSession *urlSession = [NSURLSession sessionWithConfiguration:sessionConfiguration];
    NSURLSessionDataTask *task = [urlSession dataTaskWithRequest:urlRequest completionHandler:^(NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
        if (error) {
            NSLog(@"error description: %@", [error description]);
            NSDictionary *dict = @{@"code": @([error code]), @"message": [error description]};
            if (self.fail) {
                [self.vcContext executeJSValueThreadSafe:self.fail args:@[dict]];
            }
        } else {
            NSError *jsonError;
            id object = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:&jsonError];
            if (jsonError) {
                NSLog(@"jsonError description: %@", [jsonError description]);
                NSDictionary *dict = @{@"code": @([jsonError code]), @"message": [jsonError description]};
                if (self.fail) {
                    [self.vcContext executeJSValueThreadSafe:self.fail args:@[dict]];
                }
            } else {
                NSLog(@"result object:%@", object);
                if (self.success) {
                    [self.vcContext executeJSValueThreadSafe:self.success args:@[object]];
                }
            }
        }
    }];
    [task resume];
}

@end
