//
//  HCNavigationViewController.m
//  Demo
//
//  Created by 刘海川 on 2019/1/9.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import "HCNavigationJsViewController.h"
#import "HCStoreJSExportImpl.h"
#import "HCKitJSExportImpl.h"
#import "HCRequestJSExportImpl.h"
#import "HCNavigationJSExportImpl.h"

@interface HCNavigationJsViewController () <UIWebViewDelegate>

@property (nonatomic, strong) UIWebView *appWebView;

@end

@implementation HCNavigationJsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor blueColor];
    [self.view addSubview:self.appWebView];
    [self hc_beginRoute];
}

#pragma mark - getter
- (UIWebView *)appWebView {
    if (!_appWebView) {
        CGRect rect = [[UIScreen mainScreen] bounds];
        _appWebView = [[UIWebView alloc] initWithFrame:rect];
        _appWebView.delegate = self;
        _appWebView.backgroundColor = [UIColor orangeColor];
    }
    return _appWebView;
}
#pragma mark - private method
- (void)hc_beginRoute {
    if (self.routerPath && ![@"" isEqualToString:self.routerPath]) {
        if ([@"path" isEqualToString:self.routerType]) {
            NSString *urlString = [NSString stringWithFormat:@"http://localhost:8080%@", self.routerPath];
            NSURL *url = [NSURL URLWithString:urlString];
            NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
            [[self getWebView] loadRequest:request];
        } else if ([@"url" isEqualToString:self.routerType]) {
            NSURL *url = [NSURL URLWithString:self.routerPath];
            NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
            [[self getWebView] loadRequest:request];
        } else {
            NSLog(@"router type 为空，无法跳转！");
        }
    } else {
        NSLog(@"router path 为空，无法跳转！");
    }
}
#pragma mark - HCNavigationDelegate
- (void)backInfo:(NSDictionary *)info {
    [self callJsBridge:@"noticeGoBack" args:@[info]];
}

#pragma mark - UIWebViewDelegate
- (void)webViewDidFinishLoad:(UIWebView *)webView {
    [self callJsBridge:@"noticeGoto" args:@[(self.routerContext?:@{})]];
}

#pragma mark - HCJSCoreBaseProtocol
- (UIWebView *)getWebView {
    return self.appWebView;
}

- (void)injectJSExportImpl:(JSContext *)jsContext {
    jsContext[@"NativeStore"] = [HCStoreJSExportImpl instance:self];
    jsContext[@"NativeKit"] = [HCKitJSExportImpl instance:self];
    jsContext[@"NativeRequest"] = [HCRequestJSExportImpl instance:self];
    self.appJSContext[@"NativeNavigator"] = [HCNavigationJSExportImpl instance:self];
}

@end
