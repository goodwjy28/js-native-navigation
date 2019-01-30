//
//  ViewController.m
//  Demo
//
//  Created by 刘海川 on 2018/12/18.
//  Copyright © 2018 刘海川. All rights reserved.
//

#import "ViewController.h"
#import "HCStoreJSExportImpl.h"
#import "HCKitJSExportImpl.h"
#import "HCRequestJSExportImpl.h"
#import "HCNavigationJSExportImpl.h"
#import "HCNavigationJsViewController.h"
#import "HCRouter.h"

@interface ViewController () <UIWebViewDelegate>

@property (weak, nonatomic) IBOutlet UIWebView *appWebView;
@property (copy, nonatomic) NSString *address;
@property (copy, nonatomic) NSDictionary *backInfo;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    _address = HCRouterBaseURL;
    [self reloadUrl:nil];
    
}
- (IBAction)reloadUrl:(id)sender {
    NSURL *url = [NSURL URLWithString:_address];
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
    [self.appWebView loadRequest:request];
}
- (IBAction)clearCache:(id)sender {
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
}
- (IBAction)testJs:(id)sender {
    NSDictionary *dict = @{@"foo":@"hello", @"bar":@YES};
    JSValue *value = [self callJsBridge:@"testJs" args:@[dict]];
    NSLog(@"测试返回值：%@", [value toString]);
}

#pragma mark - UIWebViewDelegate
- (void)webViewDidStartLoad:(UIWebView *)webView {
    NSLog(@"webViewDidStartLoad");
}

- (void)webViewDidFinishLoad:(UIWebView *)webView {
    NSLog(@"webViewDidFinishLoad");
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error {
    NSLog(@"didFailLoadWithError:%@", error);
}

#pragma mark - HCJSCoreBaseProtocol
- (UIWebView *)getWebView {
    return self.appWebView;
}

- (void)injectJSExportImpl:(JSContext *)jsContext {
    jsContext[@"NativeStore"] = [HCStoreJSExportImpl instance:self];
    jsContext[@"NativeKit"] = [HCKitJSExportImpl instance:self];
    jsContext[@"NativeRequest"] = [HCRequestJSExportImpl instance:self];
    jsContext[@"NativeNavigator"] = [HCNavigationJSExportImpl instance:self];
}

@end
